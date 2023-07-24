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
            Ingredient ingredient=new Ingredient();
            ingredient.setName(r.getIngredientName());
            ingredientRepository.save(ingredient);

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

    //레시피 id로 조회 (레시피 제목, 설명, steps, 작성자 이름)
    @Transactional(readOnly = true)
    public RecipeFindByIdResponseDto findRecipeByRecipeId(Long id){
        Optional<Recipe> findRecipe = recipeRepository.findById(id);
        Member member = findRecipe.get().getMember();
        List<Step> steps=findRecipe.get().getSteps();
        List<RecipeIngredient> recipeIngredients=findRecipe.get().getRecipeIngredients();
        return new RecipeFindByIdResponseDto(findRecipe.get(),member,steps,recipeIngredients);
    }

    //작성자 id로 조회 (레시피 제목, 설명, steps, 작성자 이름)
    @Transactional(readOnly = true)
    public RecipeFindByMemberIdResponseDto findRecipeByMemberId(Long id){
        Optional<Member> findMember = memberRepository.findById(id);
        List<Recipe> findRecipe = recipeRepository.findByMemberId(id);
        return new RecipeFindByMemberIdResponseDto(findMember.get(),findRecipe);
    }
}
