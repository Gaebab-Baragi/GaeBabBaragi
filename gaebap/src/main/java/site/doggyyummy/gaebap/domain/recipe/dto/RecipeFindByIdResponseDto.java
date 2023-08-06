package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Step;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecipeFindByIdResponseDto {
    private String title;
    private String description;
    private String imgUrl;
    private String videoUrl;
    private Long hit;
    private LocalDateTime writtenTime;

    private int statusCode;
    private String errorMessage;

    private MemberDto member;
    private List<StepDto> steps;
    private List<RecipeIngredientDto> recipeIngredients;
    private List<IngredientDto> ingredients;

    public RecipeFindByIdResponseDto(int statusCode,String errorMessage){
        this.statusCode=statusCode;
        this.errorMessage=errorMessage;
    }
    public RecipeFindByIdResponseDto(String title, Long hit,LocalDateTime writtenTime, String description, MemberDto member, List<StepDto> steps, List<RecipeIngredientDto> recipeIngredients,List<IngredientDto> ingredients){
        this.title=title;
        this.hit=hit;
        this.writtenTime=writtenTime;
        this.description=description;
        this.member=member;
        this.steps=steps;
        this.recipeIngredients=recipeIngredients;
        this.ingredients=ingredients;
    }
    public RecipeFindByIdResponseDto(Recipe recipe, Member member, List<Step> steps,List<RecipeIngredient> recipeIngredients,List<Ingredient> ingredients){
        this.title=recipe.getTitle();
        this.description=recipe.getDescription();
        this.hit=recipe.getHit();
        this.writtenTime=recipe.getWrittenTime();
        this.imgUrl=recipe.getImageUrl();
        this.videoUrl=recipe.getVideoUrl();
        this.member=new MemberDto(member.getUsername());
        // Convert List<Step> to List<StepDto>
        this.steps = steps.stream()
                .map(step -> new StepDto(step.getOrderingNumber(), step.getDescription()))
                .collect(Collectors.toList());
        this.recipeIngredients=recipeIngredients.stream()
                .map(recipeIngredient->new RecipeIngredientDto(recipeIngredient.getAmount()))
                .collect(Collectors.toList());
        this.ingredients=ingredients.stream()
                .map(ingredient->new IngredientDto(ingredient.getName()))
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
        public RecipeIngredientDto(String amount){
            this.amount=amount;
        }
    }
    @Getter
    public static class IngredientDto{
        private String name;
        public IngredientDto(String name){
            this.name=name;
        }
    }
}
