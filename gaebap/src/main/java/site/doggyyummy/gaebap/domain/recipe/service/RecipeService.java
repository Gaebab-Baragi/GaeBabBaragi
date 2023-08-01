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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final StepRepository stepRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    //AWS S3
    private final AmazonS3 awsS3Client;


    //레시피 등록
    //예외가 발생해도 DB에서 id는 계속 증가하는 문제 발생.. 어캐 해결하지 ㅅㅂ
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public RecipeUploadResponseDto uploadRecipe(RecipeUploadRequestDto reqDto){
        //로그인 안되어있으면 로그인 exception handling
        Optional<Member> findMeberById = memberRepository.findById(reqDto.getMember().getId());

        Recipe recipe=new Recipe();
        if(reqDto.getTitle()==null || reqDto.getTitle().equals("")){
            throw new IllegalArgumentException("제목을 입력하세요");
        }else {
            recipe.setTitle(reqDto.getTitle());
        }
        if(reqDto.getDescription()==null || reqDto.getDescription().equals("")){
            throw new IllegalArgumentException("설명을 입력하세요");
        }else{
            recipe.setDescription(reqDto.getDescription());
        }
        if(reqDto.getImgLocalPath()==null || reqDto.getImgLocalPath().equals("")){
            throw new IllegalArgumentException("대표 사진을 등록해주세요");
        }

        List<Step> steps=new ArrayList<>();
        for(RecipeUploadRequestDto.StepDto s:reqDto.getSteps()){
            if(s.getDescription()==null || s.getDescription().equals("")){
                throw new IllegalArgumentException("조리 순서에 대한 설명을 작성해주세요");
            }
            Step step=new Step();
            step.setOrderingNumber(s.getOrderingNumber());
            step.setDescription(s.getDescription());
            step.setImgUrl(s.getImgLocalPath());
            steps.add(step);
        }
        stepRepository.saveAll(steps);

        recipe.setMember(findMeberById.get());
        recipe.setSteps(steps);
        recipe.setNowTime(LocalDateTime.now());
        Recipe savedRecipe = recipeRepository.save(recipe);

        List<RecipeIngredient> recipeIngredients=new ArrayList<>();
        for(RecipeUploadRequestDto.RecipeIngredientDto r:reqDto.getRecipeIngredients()){
            if(r.getIngredientName()==null || r.getIngredientName().equals("")){
                throw new IllegalArgumentException("재료 명을 입력해주세요");
            }
            if(r.getAmount()==null || r.getAmount().equals("")){
                throw new IllegalArgumentException("수량을 입력해주세요");
            }
            Ingredient findIngredient = ingredientRepository.findByName(r.getIngredientName());
            //이미 ingredient 테이블에 같은 이름이 있는 데이터는 저장 X
            Ingredient ingredient=new Ingredient();
            if(findIngredient==null){
                ingredient.setName(r.getIngredientName());
                ingredientRepository.save(ingredient);
            }else{
                ingredient=findIngredient;
            }

            RecipeIngredient recipeIngredient=new RecipeIngredient();
            recipeIngredient.setAmount(r.getAmount());
            recipeIngredient.setIngredient(ingredient);
            recipeIngredients.add(recipeIngredient);
        }
        recipeIngredientRepository.saveAll(recipeIngredients);
        recipe.setRecipeIngredients(recipeIngredients);
        //레시피 대표 사진 및 동영상 업로드
        uploadModifyS3Object(null, reqDto.getImgLocalPath(), savedRecipe, true, null);
        uploadModifyS3Object(null,reqDto.getVideoLocalPath(),savedRecipe,false,null);
        for(Step step:steps){
            uploadModifyS3Object(null,step.getImgUrl(),savedRecipe,false,step);
            stepRepository.save(step);
        }
        savedRecipe.setSteps(steps);
        savedRecipe = recipeRepository.save(recipe);
        return new RecipeUploadResponseDto(savedRecipe.getTitle(),findMeberById.get(),HttpStatus.SC_OK,"upload success");
    }

    //레시피 id로 조회 (레시피 제목, 설명,재료, steps, 작성자 이름)
    //readOnly=true일 때, 지연 로딩 불가 !!! -> 이거 해결 어캐하지
    @Transactional(readOnly = true)
    public RecipeFindByIdResponseDto findRecipeByRecipeId(Long id){

        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        if(!findRecipe.isPresent()){
            throw new NotFoundRecipeException(HttpStatus.SC_BAD_REQUEST,"해당 레시피는 존재하지 않습니다.");
        }

        Member member = findRecipe.get().getMember();
        List<Step> steps=findRecipe.get().getSteps();
        List<RecipeIngredient> recipeIngredients=findRecipe.get().getRecipeIngredients();
        List<Ingredient> ingredients=new ArrayList<>();
        for(RecipeIngredient r:recipeIngredients){
            Ingredient ingredient=new Ingredient();
            ingredient=r.getIngredient();
            ingredients.add(ingredient);
        }

        return new RecipeFindByIdResponseDto(findRecipe.get(),member,steps,recipeIngredients,ingredients);
    }
    
    //hit 증가
    @Transactional
    public void addHit(Long id){
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        if(!findRecipe.isPresent()){
            throw new NotFoundRecipeException(HttpStatus.SC_BAD_REQUEST,"해당 레시피는 존재하지 않습니다.");
        }
        recipeRepository.addHits(id);
    }
    //작성자 id로 조회 (레시피 제목)
    @Transactional(readOnly = true)
    public RecipeFindByMemberIdResponseDto findRecipeByMemberId(Long id){
        Optional<Member> findMember = memberRepository.findById(id);
        List<Recipe> findRecipe = recipeRepository.findByMemberId(id);
        return new RecipeFindByMemberIdResponseDto(findMember.get(),findRecipe);
    }

    //레시피 all 조회 (레시피 제목, 작성자 이름)
    @Transactional(readOnly = true)
    public RecipeAllResponseDto allRecipe(){
        List<Recipe> allRecipes = recipeRepository.findAll();

        return new RecipeAllResponseDto(allRecipes);
    }

    //레시피 삭제
    @Transactional
    public RecipeDeleteResponseDto deleteRecipe(Long id,RecipeDeleteRequestDto reqDto){
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        //로그인 안하면 exception 터져
        //삭제할 findRecipe가 존재하지 않으면 exception 터져 -> 완료
        if(!findRecipe.isPresent()){
            throw new NotFoundRecipeException(HttpStatus.SC_BAD_REQUEST,"존재하지 않는 레시피입니다.");
        }
        if(findRecipe.get().getMember().getId().equals(reqDto.getLoginMember().getId())) {
            //S3에 해당 레시피 이미지 및 동영상 삭제
            String imgUrl = findRecipe.get().getImageUrl();
            String[] UrlSegments = imgUrl.split("/");
            String folderKey = UrlSegments[UrlSegments.length - 2] + "/";
            ObjectListing objectListing = awsS3Client.listObjects("sh-bucket", folderKey);
            // 폴더 내의 모든 객체들을 삭제
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                awsS3Client.deleteObject("sh-bucket", objectSummary.getKey());
            }
            // 폴더 삭제
            awsS3Client.deleteObject("sh-bucket", folderKey);

            recipeRepository.delete(findRecipe.get());

            return new RecipeDeleteResponseDto(findRecipe.get());
        }
        else{ //작성자랑 로그인한 회원의 id가 다르면 권한 없는 exceptino 발생
            throw new UnauthorizedException(HttpStatus.SC_UNAUTHORIZED,"권한이 없습니다.");
        }

    }


    //AWS S3에 사진 or 동영상 업로드 또는 수정 메서드
    public void uploadModifyS3Object(String oldUrl, String reqUrl,Recipe recipe, boolean isImg,Step step){ //oldUrl : DB에 있던 URL, reqUrl : 새로운 URL, isImg=true -> img, isImg=false -> video
        if(oldUrl==null){ //DB에 저장안되어있으니까, 새로운 객체를 s3에 저장
            if(reqUrl!=null) {
                File img = new File(reqUrl);
                String bucketName = "sh-bucket";
                if(step==null){
                    String imgKey = recipe.getId() + "/" + img.toPath().getFileName().toString();
                    String newUrl = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + imgKey;
                    awsS3Client.putObject(new PutObjectRequest(bucketName, imgKey, img));
                    if(isImg){
                        recipe.setImageUrl(newUrl);
                    }else{
                        recipe.setVideoUrl(newUrl);
                    }
                }else{ //스텝 별 사진 등록
                    String imgKey = recipe.getId() + "/"+step.getId()+"/" + img.toPath().getFileName().toString();
                    String newUrl = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + imgKey;
                    awsS3Client.putObject(new PutObjectRequest(bucketName, imgKey, img));
                    step.setImgUrl(newUrl);

                }
            }
        }else{ //DB에 저장 되어 있으니까, 수정하거나 삭제하거나.
            String newUrl=null;
            String[] imgSegments = oldUrl.split("/");
            if(reqUrl==null){//imgUrl을 삭제 한 경우 (등록되있던 사진 삭제한 경우)
                String oldImgKey=null;
                if(step==null) {
                    oldImgKey = imgSegments[imgSegments.length - 2] + "/" + imgSegments[imgSegments.length - 1];
                }else{
                    oldImgKey=imgSegments[imgSegments.length - 3] + "/"+imgSegments[imgSegments.length - 2] + "/" + imgSegments[imgSegments.length - 1];
                }
                //s3에 기존에 있던 객체 삭제 해야함
                awsS3Client.deleteObject("sh-bucket",oldImgKey);
                if(step==null){
                    if(isImg){
                        recipe.setImageUrl(newUrl);
                    }else{
                        recipe.setVideoUrl(newUrl);
                    }
                }else{ //스텝 별 사진 등록
                    step.setImgUrl(newUrl);
                }
            }
            else if(!oldUrl.equals(reqUrl)){ //이미지 파일을 수정한 경우
                String bucketName="sh-bucket";
                String oldImgKey=null;
                if(step==null) {
                    oldImgKey = imgSegments[imgSegments.length - 2] + "/" + imgSegments[imgSegments.length - 1];

                }else{
                    oldImgKey=imgSegments[imgSegments.length - 3] + "/"+imgSegments[imgSegments.length - 2] + "/" + imgSegments[imgSegments.length - 1];
                }
                //s3에 기존에 있던 객체는 삭제
                awsS3Client.deleteObject(bucketName,oldImgKey);
                //s3에 새로운 사진 저장
                File img=new File(reqUrl);
                if(step==null) {
                    String imgKey = recipe.getId().toString() + "/" + img.toPath().getFileName().toString();
                    newUrl = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + imgKey;
                    awsS3Client.putObject(new PutObjectRequest(bucketName, imgKey, img));
                }
                else{
                    String imgKey = recipe.getId() + "/"+step.getId()+"/" + img.toPath().getFileName().toString();
                    newUrl = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + imgKey;
                    awsS3Client.putObject(new PutObjectRequest(bucketName, imgKey, img));
                }
                if(step==null){
                    if(isImg){
                        recipe.setImageUrl(newUrl);
                    }else{
                        recipe.setVideoUrl(newUrl);
                    }
                }else{ //스텝 별 사진 등록
                    step.setImgUrl(newUrl);
                }
            }

        }

    }
    //레시피 수정 (등록자와 일치해야 수정 가능)
    @Transactional
    public void modifyRecipe(Long id,RecipeModifyRequestDto reqDto){
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        //해당 레시피 없으면 exception
        if(!findRecipe.isPresent()){
            throw new NotFoundRecipeException(HttpStatus.SC_BAD_REQUEST,"존재하지 않는 레시피입니다.");
        }

        Recipe recipe = findRecipe.get();
        Member writer = recipe.getMember();
        RecipeModifyRequestDto.MemberDto loginMember = reqDto.getLoginMember();
        if(loginMember==null){
            //로그인하라고 exception
        }

        if(loginMember!=null && writer.getId()==loginMember.getId()){
            findRecipe.get().setTitle(reqDto.getTitle());
            findRecipe.get().setDescription(reqDto.getDescription());

            String imgUrl=findRecipe.get().getImageUrl();
            uploadModifyS3Object(imgUrl,reqDto.getImgUrl(),findRecipe.get(),true,null);
            String videoUrl=findRecipe.get().getVideoUrl();
            uploadModifyS3Object(videoUrl,reqDto.getVideoUrl(),findRecipe.get(),false,null);

            List<Step> updateSteps=new ArrayList<>();

            for(RecipeModifyRequestDto.StepDto s:reqDto.getSteps()){
                Step findStep = stepRepository.findByRecipeIdAndOrderingNumber(id, s.getOrderingNumber());
                if(findStep!=null){ //해당 id의 step이 있으면 수정하는거
                    findStep.setOrderingNumber(s.getOrderingNumber());
                    findStep.setDescription(s.getDescription());
                    updateSteps.add(findStep);
                    uploadModifyS3Object(findStep.getImgUrl(), s.getImgUrl(),findRecipe.get(),false,findStep);
                    stepRepository.save(findStep);
                }else{ //없으면, 추가해줘야대
                    Step step=new Step();
                    step.setOrderingNumber(s.getOrderingNumber());
                    step.setDescription(s.getDescription());
                    updateSteps.add(step);
                    uploadModifyS3Object(findStep.getImgUrl(), s.getImgUrl(),findRecipe.get(),false,step);
                    stepRepository.save(step);
                }

            }

            List<Step> originSteps = findRecipe.get().getSteps();
            for(Step originStep : originSteps){
                boolean flag=false;
                for(Step updateStep:updateSteps){
                    if(updateStep.getId()==originStep.getId()){
                        flag=true;
                        break;
                    }
                }
                if(flag==false){
                    if(originStep.getImgUrl()!=null){
                        uploadModifyS3Object(originStep.getImgUrl(),null,findRecipe.get(),false,originStep);
                    }
                    stepRepository.delete(originStep);
                }
            }
            recipe.setSteps(updateSteps);

            List<RecipeIngredient> updateRecipeIngredients=new ArrayList<>();
            for(RecipeModifyRequestDto.RecipeIngredientDto r: reqDto.getRecipeIngredients()) {
                Ingredient findIngredient = ingredientRepository.findByName(r.getIngredientName());//ingredient찾음

                Ingredient ingredient = new Ingredient();
                if (findIngredient == null) {
                    ingredient.setName(r.getIngredientName());
                    ingredientRepository.save(ingredient);
                } else {
                    ingredient = findIngredient;
                }
                RecipeIngredient findRecipeIngredient=recipeIngredientRepository.findByIngredientIdAndRecipeId(ingredient.getId(), id);

                RecipeIngredient recipeIngredient = new RecipeIngredient();

                if(findRecipeIngredient==null){
                    recipeIngredient.setAmount(r.getAmount());
                    recipeIngredient.setIngredient(ingredient);
                    updateRecipeIngredients.add(recipeIngredient);
                    recipeIngredientRepository.save(recipeIngredient);
                }else{
                    recipeIngredient=findRecipeIngredient;
                    recipeIngredient.setAmount(r.getAmount());
                    recipeIngredient.setIngredient(ingredient);
                    updateRecipeIngredients.add(recipeIngredient);
                }
            }

            List<RecipeIngredient> originRecipeIngredients=findRecipe.get().getRecipeIngredients();
            for(RecipeIngredient origin:originRecipeIngredients){
                boolean flag=false;
                for(RecipeIngredient updateRecipeIngredient: updateRecipeIngredients) {
                    if(origin.getIngredient().getId()==updateRecipeIngredient.getIngredient().getId()) {
                        flag = true;
                        break;
                    }
                }
                if(flag==false) {
                    recipeIngredientRepository.delete(origin);
                }
            }
            recipe.setRecipeIngredients(updateRecipeIngredients);
            recipeRepository.save(recipe);
        }
        else{//작성자랑 수정하려는 자가 다르면 수정 불가 -> 삭제도 이거 추가해야 함
            //권한 없음 exception
            throw new UnauthorizedException(HttpStatus.SC_UNAUTHORIZED,"권한이 없습니다");
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
    @Transactional(readOnly = true)
    public RecipeSearchLikeResponseDto searchRecipeLike(RecipeSearchLikeRequestDto reqDto){
        if(reqDto.getIngredients()==null || reqDto.getIngredients().size()==0){
            List<Recipe> recipes = recipeRepository.findByTitleContaining(reqDto.getTitle());
            return new RecipeSearchLikeResponseDto(recipes);
        }else{
            List<RecipeSearchLikeRequestDto.IngredientDto> ingredients = reqDto.getIngredients();
            List<String> ingredientsName=new ArrayList<>();
            for(RecipeSearchLikeRequestDto.IngredientDto i:ingredients){
                ingredientsName.add(i.getName());
                System.out.println("i.getName() = " + i.getName());
            }
            List<Object[]> resultList= recipeIngredientRepository.findRecipesWithIngredientsAndTitle(ingredientsName,reqDto.getTitle(),ingredientsName.size());

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
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String s3FileName= UUID.randomUUID()+"-"+multipartFile.getOriginalFilename();

        ObjectMetadata objMeta=new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        awsS3Client.putObject("sh-bucket",s3FileName,multipartFile.getInputStream(),objMeta);
        return awsS3Client.getUrl("sh-bucket",s3FileName).toString();
    }

}
