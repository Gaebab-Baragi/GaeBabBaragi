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
    private List<StepDto> steps;
    private List<RecipeIngredientDto> recipeIngredients;

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
    public static class StepDto{
        private Long orderingNumber;
        private String description;
        public String getDescription(){
            if(description==null){
                return null;
            }
            return description.trim();
        }

    }
    @Getter
    public static class RecipeIngredientDto {
        private String ingredientName;
        public String getIngredientName(){
            if(ingredientName==null){
                return null;
            }
            return ingredientName.trim();
        }
        private String amount;
        public String getAmount(){
            if(amount==null){
                return null;
            }
            return amount.trim();
        }
    }
}