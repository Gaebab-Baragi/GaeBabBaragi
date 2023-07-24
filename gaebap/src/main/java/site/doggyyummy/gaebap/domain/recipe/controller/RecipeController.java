package site.doggyyummy.gaebap.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.recipe.dto.*;
import site.doggyyummy.gaebap.domain.recipe.service.RecipeService;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/recipes/new")
    public RecipeUploadResponseDto uploadRecipes(@RequestBody RecipeUploadRequestDto recipeUploadRequestDto){
        return recipeService.uploadRecipe(recipeUploadRequestDto);
    }

    @GetMapping("/recipes/{id}")
    public RecipeFindByIdResponseDto findByRecipeId(@PathVariable Long id){
        return recipeService.findRecipeByRecipeId(id);
    }

    @GetMapping("/recipes/writer/{member_id}")
    public RecipeFindByMemberIdResponseDto findRecipeByMemberId(@PathVariable ("member_id") Long id){
        return recipeService.findRecipeByMemberId(id);
    }

    @PostMapping("/members/new")
    public SignupResponseDto signUp(@RequestBody SignupRequestDto reqDto){
        return recipeService.signUp(reqDto);
    }

}