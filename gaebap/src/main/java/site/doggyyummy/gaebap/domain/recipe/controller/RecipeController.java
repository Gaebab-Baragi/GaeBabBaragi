package site.doggyyummy.gaebap.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.doggyyummy.gaebap.domain.recipe.dto.RecipeUploadReqDto;
import site.doggyyummy.gaebap.domain.recipe.dto.RecipeUploadResDto;
import site.doggyyummy.gaebap.domain.recipe.service.RecipeService;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/recipes/new")
    public RecipeUploadResDto allRecipes(@RequestBody RecipeUploadReqDto recipeUploadReqDto){
        return recipeService.uploadRecipe(recipeUploadReqDto);
    }
}