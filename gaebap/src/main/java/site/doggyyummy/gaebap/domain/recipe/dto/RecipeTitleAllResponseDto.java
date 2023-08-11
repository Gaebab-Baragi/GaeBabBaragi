package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecipeTitleAllResponseDto {
    private List<RecipeDto> recipes;

    public RecipeTitleAllResponseDto(List<Recipe> recipes){
        this.recipes=recipes.stream()
                .map(recipe -> new RecipeDto(recipe.getTitle()))
                .collect(Collectors.toList());
    }

    @Getter
    public static class RecipeDto{
        private String title;
        public RecipeDto(String title){
            this.title=title;
        }
    }
}
