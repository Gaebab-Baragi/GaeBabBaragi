package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

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
                .map(recipe -> new RecipeFindByMemberIdResponseDto.RecipeDto(recipe.getTitle()))
                .collect(Collectors.toList());
    }
    @Getter
    public static class RecipeDto{
        private String title;
        public RecipeDto(String title){
            this.title=title;
        }
    }
}
