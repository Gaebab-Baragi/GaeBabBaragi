package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;

import java.util.List;

@Getter
@Setter
public class RecipeUploadRequestDto {
    private String title;
    private String description;
    private MemberDto member; //레시피 등록자
    private List<StepDto> steps;
    private List<RecipeIngredientDto> recipeIngredients;
    @Getter
    public static class MemberDto{
        private Long id;
    }

    @Getter
    public static class StepDto{
        private Long orderingNumber;
        private String description;
    }
    @Getter
    public static class RecipeIngredientDto {
        private Long recipeId;
        private Long ingredientId;
        private String ingredientName;
        private String amount;
    }
}