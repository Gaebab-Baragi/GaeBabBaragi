package site.doggyyummy.gaebap.domain.recipe.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.pet.repository.PetRepository;
import site.doggyyummy.gaebap.domain.recipe.dto.*;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Step;
import site.doggyyummy.gaebap.domain.recipe.exception.NotFoundRecipeException;
import site.doggyyummy.gaebap.domain.recipe.exception.UnauthorizedException;
import site.doggyyummy.gaebap.domain.recipe.repository.IngredientRepository;
import site.doggyyummy.gaebap.domain.recipe.repository.RecipeIngredientRepository;
import site.doggyyummy.gaebap.domain.recipe.repository.RecipeRepository;
import site.doggyyummy.gaebap.domain.recipe.repository.StepRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final StepRepository stepRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final PetRepository petRepository;
    //AWS S3
    private final AmazonS3 awsS3Client;


    //레시피 등록
    //예외가 발생해도 DB에서 id는 계속 증가하는 문제 발생.. 어캐 해결하지 ㅅㅂ
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public RecipeUploadResponseDto uploadRecipe(Member member,RecipeUploadRequestDto reqDto, MultipartFile recipeImage, MultipartFile recipeVideo, MultipartFile[] stepImages) throws IOException {
        Recipe recipe = new Recipe();
        if (reqDto.getTitle() == null || reqDto.getTitle().equals("")) {
            throw new IllegalArgumentException("제목을 입력하세요");
        } else {
            recipe.setTitle(reqDto.getTitle());
        }
        if (reqDto.getDescription() == null || reqDto.getDescription().equals("")) {
            throw new IllegalArgumentException("설명을 입력하세요");
        } else {
            recipe.setDescription(reqDto.getDescription());
        }
        if (recipeImage.isEmpty()) {
            throw new IllegalArgumentException("레시피 대표 사진을 등록해주세요");
        }
        recipe.setMember(member);
        recipeRepository.save(recipe);
        List<Step> steps = new ArrayList<>();
        for (RecipeUploadRequestDto.StepDto s : reqDto.getSteps()) {
            if (s.getDescription() == null || s.getDescription().equals("")) {
                throw new IllegalArgumentException("조리 순서에 대한 설명을 작성해주세요");
            }
            Step step = new Step();
            step.setOrderingNumber(s.getOrderingNumber());
            step.setDescription(s.getDescription());
            step.setRecipe(recipe);
            Map<String, String> stepMap = uploadFile(step, stepImages[s.getOrderingNumber().intValue() - 1]);
            if (stepMap != null) {
                step.setS3Url(stepMap.get("s3Url"));
                step.setS3Key(stepMap.get("s3Key"));
            }
            steps.add(step);
        }
        stepRepository.saveAll(steps);

        recipe.setSteps(steps);
        recipe.setWrittenTime(LocalDateTime.now());
        recipeRepository.save(recipe);

        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        for (RecipeUploadRequestDto.RecipeIngredientDto r : reqDto.getRecipeIngredients()) {
            if (r.getIngredientName() == null || r.getIngredientName().equals("")) {
                throw new IllegalArgumentException("재료 명을 입력해주세요");
            }
            if (r.getAmount() == null || r.getAmount().equals("")) {
                throw new IllegalArgumentException("수량을 입력해주세요");
            }
            Ingredient findIngredient = ingredientRepository.findByName(r.getIngredientName());
            //이미 ingredient 테이블에 같은 이름이 있는 데이터는 저장 X
            Ingredient ingredient = new Ingredient();
            if (findIngredient == null) {
                ingredient.setName(r.getIngredientName());
                ingredientRepository.save(ingredient);
            } else {
                ingredient = findIngredient;
            }

            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setAmount(r.getAmount());
            recipeIngredient.setIngredient(ingredient);
            recipeIngredients.add(recipeIngredient);
        }
        recipeIngredientRepository.saveAll(recipeIngredients);
        recipe.setRecipeIngredients(recipeIngredients);
        //레시피 대표 사진 및 동영상 업로드
        Map<String, String> ImageMap = uploadFile(recipe, recipeImage);
        recipe.setImageUrl(ImageMap.get("s3Url"));
        recipe.setImageKey(ImageMap.get("s3Key"));

        if (!recipeVideo.isEmpty()) {
            Map<String, String> VideoMap = uploadFile(recipe, recipeVideo);
            recipe.setVideoUrl(VideoMap.get("s3Url"));
            recipe.setVideoKey(VideoMap.get("s3Key"));
        }
        recipe.setSteps(steps);
        recipe = recipeRepository.save(recipe);
        return new RecipeUploadResponseDto(recipe.getTitle(), member, HttpStatus.SC_OK, "upload success");
    }

    //레시피 id로 조회 (레시피 제목, 설명,재료, steps, 작성자 이름)
    //readOnly=true일 때, 지연 로딩 불가 !!! -> 이거 해결 어캐하지
    @Transactional(readOnly = true)
    public RecipeFindByIdResponseDto findRecipeByRecipeId(Long id) {

        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        if (!findRecipe.isPresent()) {
            throw new NotFoundRecipeException(HttpStatus.SC_BAD_REQUEST, "해당 레시피는 존재하지 않습니다.");
        }

        Member member = findRecipe.get().getMember();
        List<Step> steps = findRecipe.get().getSteps();
        List<RecipeIngredient> recipeIngredients = findRecipe.get().getRecipeIngredients();
        List<Ingredient> ingredients = new ArrayList<>();
        for (RecipeIngredient r : recipeIngredients) {
            Ingredient ingredient = new Ingredient();
            ingredient = r.getIngredient();
            ingredients.add(ingredient);
        }

        return new RecipeFindByIdResponseDto(findRecipe.get(), member, steps, recipeIngredients, ingredients);
    }

    //hit 증가
    @Transactional
    public void addHit(Long id) {
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        if (!findRecipe.isPresent()) {
            throw new NotFoundRecipeException(HttpStatus.SC_BAD_REQUEST, "해당 레시피는 존재하지 않습니다.");
        }
        recipeRepository.addHits(id);
    }

    //작성자 id로 조회 (레시피 제목)
    @Transactional(readOnly = true)
    public RecipeFindByMemberIdResponseDto findRecipeByMemberId(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        List<Recipe> findRecipe = recipeRepository.findByMemberId(id);
        return new RecipeFindByMemberIdResponseDto(findMember.get(), findRecipe);
    }

    //레시피 all 조회 (레시피 제목, 작성자 이름)
    @Transactional(readOnly = true)
    public RecipeAllResponseDto allRecipe() {
        List<Recipe> allRecipes = recipeRepository.findAll();

        return new RecipeAllResponseDto(allRecipes);
    }

    //레시피 삭제
    @Transactional
    public RecipeDeleteResponseDto deleteRecipe(Long id, RecipeDeleteRequestDto reqDto) {
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        //로그인 안하면 exception 터져
        Recipe recipe = findRecipe.get();
        if (recipe == null) {
            throw new NotFoundRecipeException(HttpStatus.SC_BAD_REQUEST, "존재하지 않는 레시피입니다.");
        }
        if (recipe.getMember().getId().equals(reqDto.getLoginMember().getId())) {
            deleteFile(recipe);
            recipeRepository.delete(recipe);
            return new RecipeDeleteResponseDto(recipe);
        } else {
            throw new UnauthorizedException(HttpStatus.SC_UNAUTHORIZED, "권한이 없습니다.");
        }

    }


    //레시피 수정 (등록자와 일치해야 수정 가능)
    @Transactional
    public void modifyRecipe(Long id, RecipeModifyRequestDto reqDto,MultipartFile newRecipeImage,MultipartFile newRecipeVideo,MultipartFile[] newStepImages) throws IOException {
        Optional<Recipe> findRecipe = recipeRepository.findById(id);

        //해당 레시피 없으면 exception
        if (!findRecipe.isPresent()) {
            throw new NotFoundRecipeException(HttpStatus.SC_BAD_REQUEST, "존재하지 않는 레시피입니다.");
        }

        Recipe recipe = findRecipe.get();
        Member writer = recipe.getMember();
        RecipeModifyRequestDto.MemberDto loginMember = reqDto.getLoginMember();
        if (loginMember == null) {
            //로그인하라고 exception
        }

        if (loginMember != null && writer.getId() == loginMember.getId()) {
            recipe.setTitle(reqDto.getTitle());
            recipe.setDescription(reqDto.getDescription());

            if(!newRecipeImage.isEmpty()){ //레시피 사진 바꿔치기
                //기존꺼는 삭제해야함
                String oldImageS3Key=recipe.getImageKey();
                awsS3Client.deleteObject("sh-bucket",oldImageS3Key);
                Map<String,String> ImageMap=uploadFile(recipe,newRecipeImage);
                recipe.setImageUrl(ImageMap.get("s3Url"));
                recipe.setImageKey(ImageMap.get("s3Key"));
            }
            if(!newRecipeVideo.isEmpty()){ //비디오는 동영상 삭제하는 메서드 추가해야함 (기존 것이 없으면 추가해야지)
                if(recipe.getVideoKey()==null){
                    Map<String, String> VideoMap = uploadFile(recipe, newRecipeVideo);
                    recipe.setVideoUrl(VideoMap.get("s3Url"));
                    recipe.setVideoKey(VideoMap.get("s3Key"));
                }else {
                    String oldVideoS3Key = recipe.getVideoKey();
                    awsS3Client.deleteObject("sh-bucket", oldVideoS3Key);
                    Map<String, String> VideoMap = uploadFile(recipe, newRecipeVideo);
                    recipe.setVideoUrl(VideoMap.get("s3Url"));
                    recipe.setVideoKey(VideoMap.get("s3Key"));
                }
            }

            List<Step> updateSteps = new ArrayList<>();
            for (RecipeModifyRequestDto.StepDto s : reqDto.getSteps()) {
                Step findStep = stepRepository.findByRecipeIdAndOrderingNumber(id, s.getOrderingNumber());
                if (findStep != null) { //해당 id의 step이 있으면 수정하는거
                    findStep.setOrderingNumber(s.getOrderingNumber());
                    findStep.setDescription(s.getDescription());
                    if(!newStepImages[s.getOrderingNumber().intValue()-1].isEmpty()){
                        if(findStep.getS3Key()==null){
                            Map<String,String> stepMap=uploadFile(findStep,newStepImages[s.getOrderingNumber().intValue()-1]);
                            findStep.setS3Key(stepMap.get("s3Key"));
                            findStep.setS3Url(stepMap.get("s3Url"));
                        }else {
                            String oldStepImageKey = findStep.getS3Key();
                            awsS3Client.deleteObject("sh-bucket", oldStepImageKey);
                            Map<String, String> stepMap = uploadFile(findStep, newStepImages[s.getOrderingNumber().intValue() - 1]);
                            findStep.setS3Key(stepMap.get("s3Key"));
                            findStep.setS3Url(stepMap.get("s3Url"));
                        }
                    }else{
                        if(findStep.getS3Key()!=null) {
                            String oldStepImageKey = findStep.getS3Key();
                            awsS3Client.deleteObject("sh-bucket", oldStepImageKey);
                        }
                    }
                    updateSteps.add(findStep);
                } else { //없으면, 추가해줘야대
                    Step step = new Step();
                    step.setOrderingNumber(s.getOrderingNumber());
                    step.setDescription(s.getDescription());
                    if(!newStepImages[s.getOrderingNumber().intValue()-1].isEmpty()){
                        Map<String,String> stepMap=uploadFile(step,newStepImages[s.getOrderingNumber().intValue()-1]);
                        step.setS3Key(stepMap.get("s3Key"));
                        step.setS3Url(stepMap.get("s3Url"));
                    }
                    updateSteps.add(step);
                }
            }
            stepRepository.saveAll(updateSteps);
            List<Step> originSteps = findRecipe.get().getSteps();
            for (Step originStep : originSteps) {
                boolean flag = false;
                for (Step updateStep : updateSteps) {
                    if (updateStep.getId() == originStep.getId()) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
//                    if(originStep.getImgUrl()!=null){
//                        uploadModifyS3Object(originStep.getImgUrl(),null,findRecipe.get(),false,originStep);
//                    }
                    stepRepository.delete(originStep);
                }
            }
            recipe.setSteps(updateSteps);

            List<RecipeIngredient> updateRecipeIngredients = new ArrayList<>();
            for (RecipeModifyRequestDto.RecipeIngredientDto r : reqDto.getRecipeIngredients()) {
                Ingredient findIngredient = ingredientRepository.findByName(r.getIngredientName());//ingredient찾음

                Ingredient ingredient = new Ingredient();
                if (findIngredient == null) {
                    ingredient.setName(r.getIngredientName());
                    ingredientRepository.save(ingredient);
                } else {
                    ingredient = findIngredient;
                }
                RecipeIngredient findRecipeIngredient = recipeIngredientRepository.findByIngredientIdAndRecipeId(ingredient.getId(), id);

                RecipeIngredient recipeIngredient = new RecipeIngredient();

                if (findRecipeIngredient == null) {
                    recipeIngredient.setAmount(r.getAmount());
                    recipeIngredient.setIngredient(ingredient);
                    updateRecipeIngredients.add(recipeIngredient);
                    recipeIngredientRepository.save(recipeIngredient);
                } else {
                    recipeIngredient = findRecipeIngredient;
                    recipeIngredient.setAmount(r.getAmount());
                    recipeIngredient.setIngredient(ingredient);
                    updateRecipeIngredients.add(recipeIngredient);
                }
            }

            List<RecipeIngredient> originRecipeIngredients = findRecipe.get().getRecipeIngredients();
            for (RecipeIngredient origin : originRecipeIngredients) {
                boolean flag = false;
                for (RecipeIngredient updateRecipeIngredient : updateRecipeIngredients) {
                    if (origin.getIngredient().getId() == updateRecipeIngredient.getIngredient().getId()) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    recipeIngredientRepository.delete(origin);
                }
            }
            recipe.setRecipeIngredients(updateRecipeIngredients);
            recipeRepository.save(recipe);
        } else {//작성자랑 수정하려는 자가 다르면 수정 불가 -> 삭제도 이거 추가해야 함
            //권한 없음 exception
            throw new UnauthorizedException(HttpStatus.SC_UNAUTHORIZED, "권한이 없습니다");
        }

    }


    public void s3DeleteAll() {
        try {
            // 모든 객체의 키(Key) 목록 가져오기
            ObjectListing objectListing = awsS3Client.listObjects("sh-bucket");

            List<DeleteObjectsRequest.KeyVersion> keysToDelete = new ArrayList<>();
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                keysToDelete.add(new DeleteObjectsRequest.KeyVersion(objectSummary.getKey()));
            }

            // 모든 객체 삭제 요청 생성
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest("sh-bucket").withKeys(keysToDelete);

            // 객체들 삭제
            DeleteObjectsResult deleteObjectsResult = awsS3Client.deleteObjects(deleteObjectsRequest);
            System.out.println("Deleted objects count: " + deleteObjectsResult.getDeletedObjects().size());
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    //레시피 제목은 like 검색, 재료는 equal로 검색
    //제목만 있는 경우 o
    //제목은 있으나 마나임
    //재료 이름만 있는 경우
    //펫 id만 있는 경우
    @Transactional(readOnly = true)
    public RecipeSearchLikeResponseDto searchRecipeLike(RecipeSearchLikeRequestDto reqDto) {
        if ((reqDto.getIngredients() == null || reqDto.getIngredients().size() == 0) && (reqDto.getPets()==null || reqDto.getPets().size()==0)) {
            System.out.println("제목만 있는 경우");
            List<Recipe> recipes = recipeRepository.findByTitleContaining(reqDto.getTitle());
            return new RecipeSearchLikeResponseDto(recipes);
        } else if(reqDto.getIngredients()!=null&&reqDto.getIngredients().size()!=0&&reqDto.getPets()==null || reqDto.getPets().size()==0){ //재료만 있는 경우
            System.out.println("재료만 있는 경우");
            List<RecipeSearchLikeRequestDto.IngredientDto> ingredients = reqDto.getIngredients();
            List<String> ingredientsName = new ArrayList<>();

            for (RecipeSearchLikeRequestDto.IngredientDto i : ingredients) {
                ingredientsName.add(i.getName());
            }

//            List<RecipeSearchLikeRequestDto.PetDto> pets=reqDto.getPets();
//            List<Long> petsId = new ArrayList<>();
//            if(reqDto.getPets()!=null && reqDto.getPets().size()!=0) {
//                for (RecipeSearchLikeRequestDto.PetDto p : pets) {
//                    petsId.add(p.getId());
//                    Pet pet=petRepository.selectOne(p.getId());
//                    System.out.println("pet.getName() = " + pet.getName());
//                }
//            }

//            List<Object[]> resultList = recipeIngredientRepository.findRecipesWithIngredientsAndTitleAndForbiddenIngredients(ingredientsName, reqDto.getTitle(), ingredientsName.size(),petsId);
            List<Object[]> resultList = recipeIngredientRepository.findRecipesWithIngredientsAndTitle(ingredientsName, reqDto.getTitle(), ingredientsName.size());

            List<Recipe> recipes = new ArrayList<>();

            for (Object[] result : resultList) {
                Long count = (Long) result[0];
                Long recipeId = (Long) result[1];

                Recipe recipe = new Recipe();
                // 이후 Recipe 엔티티의 필드에 값을 설정하는 로직 추가
                // recipe.setCount(count);
                recipe.setId(recipeId);
                recipe.setTitle(recipeRepository.findById(recipeId).get().getTitle());
                recipe.setMember(recipeRepository.findById(recipeId).get().getMember());
                recipes.add(recipe);
            }
            return new RecipeSearchLikeResponseDto(recipes);
        }else if(reqDto.getPets()!=null && reqDto.getPets().size()!=0 && reqDto.getIngredients()==null || reqDto.getIngredients().size()==0){ //펫만 있는 경우
            List<RecipeSearchLikeRequestDto.PetDto> pets=reqDto.getPets();
            List<Long> petsId = new ArrayList<>();
            System.out.println("팻만 있는 경우");
            for (RecipeSearchLikeRequestDto.PetDto p : pets) {
                petsId.add(p.getId());
                Pet pet=petRepository.selectOne(p.getId());
                System.out.println("pet.getName() = " + pet.getName());
            }
            List<Object[]> resultList = recipeIngredientRepository.findRecipesWithForbiddenIngredientsAndTitle(petsId, reqDto.getTitle());
            List<Recipe> recipes = new ArrayList<>();

            for (Object[] result : resultList) {
                Long count = (Long) result[0];
                Long recipeId = (Long) result[1];

                Recipe recipe = new Recipe();
                // 이후 Recipe 엔티티의 필드에 값을 설정하는 로직 추가
                // recipe.setCount(count);
                recipe.setId(recipeId);
                recipe.setTitle(recipeRepository.findById(recipeId).get().getTitle());
                recipe.setMember(recipeRepository.findById(recipeId).get().getMember());
                recipes.add(recipe);
            }
            return new RecipeSearchLikeResponseDto(recipes);
        }else{
            System.out.println("둘다 있는 경우");
            List<RecipeSearchLikeRequestDto.IngredientDto> ingredients = reqDto.getIngredients();
            List<String> ingredientsName = new ArrayList<>();

            for (RecipeSearchLikeRequestDto.IngredientDto i : ingredients) {
                ingredientsName.add(i.getName());
            }
            List<RecipeSearchLikeRequestDto.PetDto> pets=reqDto.getPets();
            List<Long> petsId = new ArrayList<>();

            for (RecipeSearchLikeRequestDto.PetDto p : pets) {
                petsId.add(p.getId());
            }
            List<Object[]> resultList = recipeIngredientRepository.findRecipesWithIngredientsAndTitleAndForbiddenIngredients(ingredientsName,reqDto.getTitle(),ingredientsName.size(),petsId);
            List<Recipe> recipes = new ArrayList<>();

            for (Object[] result : resultList) {
                Long count = (Long) result[0];
                Long recipeId = (Long) result[1];

                Recipe recipe = new Recipe();
                // 이후 Recipe 엔티티의 필드에 값을 설정하는 로직 추가
                // recipe.setCount(count);
                recipe.setId(recipeId);
                recipe.setTitle(recipeRepository.findById(recipeId).get().getTitle());
                recipe.setMember(recipeRepository.findById(recipeId).get().getMember());
                recipes.add(recipe);
            }
            return new RecipeSearchLikeResponseDto(recipes);
        }

    }

    //aws s3에 업로드
    public Map<String, String> uploadFile(Object object, MultipartFile multipartFile) throws IOException {
        String s3Key = "";
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        if (object instanceof Recipe) {
            s3Key = "recipe/" + ((Recipe) object).getId() + "/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        }
        if (object instanceof Step) {
            s3Key = "recipe/" + ((Step) object).getRecipe().getId() + "/step/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        }

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(multipartFile.getInputStream().available());

        awsS3Client.putObject("sh-bucket", s3Key, multipartFile.getInputStream(), objMeta);
        String s3Url = awsS3Client.getUrl("sh-bucket", s3Key).toString();
        Map<String, String> map = new HashMap<>();
        map.put("s3Key", s3Key);
        map.put("s3Url", s3Url);
        return map;
    }

    //레시피 삭제
    public void deleteFile(Recipe recipe) {
        String folderKey = "recipe/"+recipe.getId()+"/";
        ObjectListing objectListing = awsS3Client.listObjects("sh-bucket", folderKey);
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            awsS3Client.deleteObject("sh-bucket", objectSummary.getKey());
        }
        awsS3Client.deleteObject("sh-bucket", folderKey);
    }



}
