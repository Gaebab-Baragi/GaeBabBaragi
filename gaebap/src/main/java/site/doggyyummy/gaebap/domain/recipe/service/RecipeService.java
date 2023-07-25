package site.doggyyummy.gaebap.domain.recipe.service;

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
                    System.out.println("=====================================");
                    System.out.println("findRecipeIngredient = " + findRecipeIngredient.getIngredient().getName());
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

}
