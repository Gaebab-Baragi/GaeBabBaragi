package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Step;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecipeFindByIdResponseDto {
    private String title;
    private String description;
    private MemberDto member;
    private List<StepDto> steps;
    private List<RecipeIngredientDto> recipeIngredients;


    public RecipeFindByIdResponseDto(String title, String description, MemberDto member, List<StepDto> steps, List<RecipeIngredientDto> recipeIngredients){
        this.title=title;
        this.description=description;
        this.member=member;
        this.steps=steps;
        this.recipeIngredients=recipeIngredients;
    }
    public RecipeFindByIdResponseDto(Recipe recipe, Member member, List<Step> steps,List<RecipeIngredient> recipeIngredients){
        this.title=recipe.getTitle();
        this.description=recipe.getDescription();
        this.member=new MemberDto(member.getName());
        // Convert List<Step> to List<StepDto>
        this.steps = steps.stream()
                .map(step -> new StepDto(step.getOrderingNumber(), step.getDescription()))
                .collect(Collectors.toList());
        this.recipeIngredients=recipeIngredients.stream()
                .map(recipeIngredient->new RecipeIngredientDto(recipeIngredient.getAmount(),recipeIngredient.getIngredient()))
                .collect(Collectors.toList());
    }

    @Getter
    public static class MemberDto{
        private String name;
        public MemberDto(String name){
            this.name=name;
        }
    }
    @Getter
    public static class StepDto{
        private Long orderingNumber;
        private String description;
        public StepDto(Long orderingNumber,String description){
            this.description=description;
            this.orderingNumber=orderingNumber;
        }
    }
    @Getter
    public static class RecipeIngredientDto{
        private String amount;
        private Ingredient ingredient;
        public RecipeIngredientDto(String amount,Ingredient ingredient){
            this.amount=amount;
            this.ingredient=ingredient;
            System.out.println("this.ingredient.getName() = " + this.ingredient.getName());
        }
    }
}
