package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeModifyRequestDto {
    private String title;
    private String description;
    //imgUrl, videoUrl 필요

    private List<RecipeIngredientDto> recipeIngredients;
    private List<StepDto> steps;
    private MemberDto member;
    private MemberDto loginMember;


    @Getter
    public static class MemberDto{
        private Long id;
    }
    @Getter
    public static class StepDto{
        private Long id;
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
