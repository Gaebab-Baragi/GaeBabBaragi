package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeSearchLikeRequestDto {
    private String title;
    private List<IngredientDto> ingredients;

    @Getter
    public static class IngredientDto{
        private String name;
    }

}
