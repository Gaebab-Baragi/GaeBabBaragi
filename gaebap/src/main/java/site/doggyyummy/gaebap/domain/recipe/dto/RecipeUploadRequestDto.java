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
    private String imgLocalPath;
    private String videoLocalPath;
    public String getTitle(){
        if(title==null){
            return null;
        }
        return title.trim();
    }
    public String getDescription(){
        if(description==null){
            return null;
        }
        return description.trim();
    }
    @Getter
    public static class MemberDto{
        private Long id;
    }

    @Getter
    public static class StepDto{
        private Long orderingNumber;
        private String description;
        public String getDescription(){
            if(description==null){
                return null;
            }
            return description.trim();
        }

        private String imgLocalPath;
    }
    @Getter
    public static class RecipeIngredientDto {
        private Long recipeId;
        private Long ingredientId;
        private String ingredientName;
        public String getIngredientName(){
            if(ingredientName==null){
                return null;
            }
            return ingredientName.trim();
        }
        private String amount;
    }
}