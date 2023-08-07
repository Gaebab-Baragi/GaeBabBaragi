package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RecipeFindByMemberIdResponseDto {
    private String name;
    private List<RecipeDto> recipes;
    public RecipeFindByMemberIdResponseDto(String name, List<RecipeDto> recipes){
        this.name=name;
        this.recipes=recipes;
    }
    public RecipeFindByMemberIdResponseDto(Member member, List<Recipe> recipes){
        this.name=member.getUsername();
        this.recipes=recipes.stream()
                .map(recipe -> new RecipeFindByMemberIdResponseDto.RecipeDto(recipe.getTitle(),recipe.getDescription(),recipe.getWrittenTime(),recipe.getHit(),recipe.getImageUrl()))
                .collect(Collectors.toList());
    }
    @Getter
    public static class RecipeDto{
        private String title;
        private String description;
        private LocalDateTime writtenTime;
        private Long hit;
        private String recipeImageUrl;
        public RecipeDto(String title,String description,LocalDateTime writtenTime,Long hit,String recipeImageUrl){
            this.title=title;
            this.description=description;
            this.writtenTime=writtenTime;
            this.hit=hit;
            this.recipeImageUrl=recipeImageUrl;
        }
    }
}
