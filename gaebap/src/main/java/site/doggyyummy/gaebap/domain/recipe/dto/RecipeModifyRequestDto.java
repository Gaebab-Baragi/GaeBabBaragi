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
    //imgUrl, videoUrl 필요 -> 아마 불필요 할듯
    private String imgUrl;
    private String videoUrl;

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
        private Long orderingNumber;
        private String description;
        private String imgUrl;
    }

    @Getter
    public static class RecipeIngredientDto{
        private Long recipeId;
        private Long ingredientId;
        private String ingredientName;
        private String amount;
    }

}
