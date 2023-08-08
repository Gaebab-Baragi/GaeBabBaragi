package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class RecipeModifyRequestDto {
    private String title;
    private String description;

    private List<RecipeIngredientDto> recipeIngredients;
    private List<StepDto> steps;

    @Getter
    public static class StepDto{
        private Long orderingNumber;
        private String description;
    }

    @Getter
    public static class RecipeIngredientDto{
        private Long recipeId;
        private Long ingredientId;
        private String ingredientName;
        private String amount;
    }

}
