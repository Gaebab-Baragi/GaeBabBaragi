package site.doggyyummy.gaebap.domain.recipe.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.domain.recipe.dto.*;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Step;
import site.doggyyummy.gaebap.domain.recipe.repository.IngredientRepository;
import site.doggyyummy.gaebap.domain.recipe.repository.RecipeIngredientRepository;
import site.doggyyummy.gaebap.domain.recipe.repository.RecipeRepository;
import site.doggyyummy.gaebap.domain.recipe.repository.StepRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    //그냥 테스트용임 (지워야됨)
    @Transactional
    public SignupResponseDto signUp(SignupRequestDto reqDto){
        Member member=new Member();
        member.setName(reqDto.getName());
        Member savedMember = memberRepository.save(member);
        return new SignupResponseDto(savedMember.getId(), savedMember.getName());
    }

    //레시피 등록 (대표 이미지, 스텝 별 이미지, 시연 영상 not included)
    @Transactional
    public RecipeUploadResponseDto uploadRecipe(RecipeUploadRequestDto reqDto){
        Optional<Member> findMeberById = memberRepository.findById(reqDto.getMember().getId());

        List<Step> steps=new ArrayList<>();
        for(RecipeUploadRequestDto.StepDto s:reqDto.getSteps()){
            Step step=new Step();
            step.setOrderingNumber(s.getOrderingNumber());
            step.setDescription(s.getDescription());
            steps.add(step);
            stepRepository.save(step);
        }

        List<RecipeIngredient> recipeIngredients=new ArrayList<>();
        for(RecipeUploadRequestDto.RecipeIngredientDto r:reqDto.getRecipeIngredients()){
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
            recipeIngredientRepository.save(recipeIngredient);
        }

        Recipe recipe=new Recipe();
        recipe.setTitle(reqDto.getTitle());
        recipe.setDescription(reqDto.getDescription());
        recipe.setMember(findMeberById.get());
        recipe.setSteps(steps);
        recipe.setRecipeIngredients(recipeIngredients);

        Recipe savedRecipe = recipeRepository.save(recipe);

        //레시피 대표 사진 업로드
        File img=new File(reqDto.getImgLocalPath());
        String imgKey=savedRecipe.getId().toString()+"/"+img.toPath().getFileName().toString();
        String bucketName="sh-bucket";
        String imgUrl="https://"+bucketName+".s3.ap-northeast-2.amazonaws.com/"+imgKey;

        ObjectMetadata objectMetadata = null;
        try{
            objectMetadata=awsS3Client.getObjectMetadata(bucketName,imgKey);
            System.out.println("버킷에 해당 사진이 이미 존재하므로, 재저장하지 않습니다.");
        }catch(AmazonS3Exception e){
            if(e.getStatusCode()==404) {
                //존재하지 않는 레시피 사진이면 업로드
                awsS3Client.putObject(new PutObjectRequest(bucketName, imgKey, img));
                System.out.println("버킷에 해당 사진을 저장했습니다.");
            }else {
                e.printStackTrace();
            }
        }
        recipe.setImageUrl(imgUrl);

        //레시피 시연 영상 업로드
        File video=new File(reqDto.getVideoLocalPath());
        String videoKey=savedRecipe.getId().toString()+"/"+video.toPath().getFileName().toString();
        String videoUrl="https://"+bucketName+".s3.ap-northeast-2.amazonaws.com/"+videoKey;
        ObjectMetadata objectMetadata2 = null;
        try{
            objectMetadata2=awsS3Client.getObjectMetadata(bucketName,videoKey);
            System.out.println("버킷에 해당 비디오가 이미 존재하므로, 재저장하지 않습니다.");
        }catch(AmazonS3Exception e){
            if(e.getStatusCode()==404) {
                //존재하지 않는 레시피 사진이면 업로드
                awsS3Client.putObject(new PutObjectRequest(bucketName, videoKey, video));
                System.out.println("버킷에 해당 비디오를 저장했습니다.");
            }else {
                e.printStackTrace();
            }
        }
        recipe.setVideoUrl(videoUrl);
        savedRecipe = recipeRepository.save(recipe);
        return new RecipeUploadResponseDto(savedRecipe.getTitle(),findMeberById.get());
    }

    //레시피 id로 조회 (레시피 제목, 설명,재료, steps, 작성자 이름)
    @Transactional(readOnly = true)
    public RecipeFindByIdResponseDto findRecipeByRecipeId(Long id){
        //hit 증가
        recipeRepository.addHits(id);
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
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
    public RecipeDeleteResponseDto deleteRecipe(Long id){
        Optional<Recipe> findRecipe = recipeRepository.findById(id);

        //S3에 해당 레시피 이미지 및 동영상 삭제
        String imgUrl=findRecipe.get().getImageUrl();
        String[] UrlSegments= imgUrl.split("/");
        String folderKey= UrlSegments[UrlSegments.length-2]+"/";
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

    //레시피 수정 (등록자와 일치해야 수정 가능)
    @Transactional
    public void modifyRecipe(Long id,RecipeModifyRequestDto reqDto){
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        Recipe recipe = findRecipe.get();
        Member writer = recipe.getMember();
        RecipeModifyRequestDto.MemberDto loginMember = reqDto.getLoginMember();

        if(loginMember!=null && writer.getId()==loginMember.getId()){
            findRecipe.get().setTitle(reqDto.getTitle());
            findRecipe.get().setDescription(reqDto.getDescription());

            //2. 삭제 가능
            String imgUrl=findRecipe.get().getImageUrl();
            System.out.println("imgUrl = " + imgUrl);
            System.out.println("reqDto.getImgUrl() = " + reqDto.getImgUrl());
            String[] imgSegments=imgUrl.split("/");
            String folderKey=imgSegments[imgSegments.length-2]+"/";

            ObjectListing objectListing = awsS3Client.listObjects("sh-bucket", folderKey);
            //https://sh-bucket.s3.ap-northeast-2.amazonaws.com/2/teayang.jpg
            if(reqDto.getImgUrl().equals("") || !imgUrl.equals(reqDto.getImgUrl())){
                String imgKey=imgSegments[imgSegments.length-1];
                System.out.println("2304oejifadklsjv;ifjskl;dfjio;awejfaweioghosdgjklsdjfl;asdjfklsdfm@!#!@#!@");
                System.out.println("imgKey = "+ imgKey);
                if(reqDto.getImgUrl().equals("")) {
                    findRecipe.get().setImageUrl("");
                }
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    System.out.println("objectSummary.getKey() = " + objectSummary.getKey());
                    System.out.println((folderKey+imgKey));
                    if(objectSummary.getKey().equals((folderKey+imgKey))){
                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        awsS3Client.deleteObject("sh-bucket",objectSummary.getKey());
                        break;
                    }
                }
            }
            if(!reqDto.getImgUrl().equals("") && !imgUrl.equals(reqDto.getImgUrl())){
                File img=new File(reqDto.getImgUrl());
                String imgKey=findRecipe.get().getId().toString()+"/"+img.toPath().getFileName().toString();
                String newImgUrl="https://sh-bucket.s3.ap-northeast-2.amazonaws.com/"+imgKey;
                ObjectMetadata objectMetadata=null;
                try{
                    objectMetadata=awsS3Client.getObjectMetadata("sh-bucket",imgKey);
                }catch (AmazonS3Exception e){
                    if(e.getStatusCode()==404){
                        findRecipe.get().setImageUrl(newImgUrl);
                        awsS3Client.putObject(new PutObjectRequest("sh-bucket",imgKey,img));
                    }else{
                        e.printStackTrace();
                    }
                }
            }









            List<Step> updateSteps=new ArrayList<>();

            for(RecipeModifyRequestDto.StepDto s:reqDto.getSteps()){
                Step findStep = stepRepository.findByRecipeIdAndOrderingNumber(id, s.getOrderingNumber());
                if(findStep!=null){ //해당 id의 step이 있으면 수정하는거
                    findStep.setOrderingNumber(s.getOrderingNumber());
                    findStep.setDescription(s.getDescription());
                    updateSteps.add(findStep);
                    stepRepository.save(findStep);
                }else{ //없으면, 추가해줘야대
                    Step step=new Step();
                    step.setOrderingNumber(s.getOrderingNumber());
                    step.setDescription(s.getDescription());
                    updateSteps.add(step);
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

}
