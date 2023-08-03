package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecipeSearchLikeResponseDto {
    private List<RecipeDto> recipes;
    public RecipeSearchLikeResponseDto(List<Recipe> recipes){
        this.recipes=recipes.stream()
                .map(recipe->new RecipeDto(recipe.getTitle(),recipe.getMember(),recipe.getId()))
                .collect(Collectors.toList());
    }
    @Getter
    public static class RecipeDto {
        private String title;
        private Long id;
        private MemberDto member;
        public RecipeDto(String title,MemberDto member,Long id){
            this.title=title;
            this.member=member;
            this.id=id;
        }
        public RecipeDto(String title, Member member,Long id){
            this.title=title;
            this.member=new MemberDto(member.getUsername());
            this.id=id;
        }
    }

    @Getter
    public static class MemberDto{
        private String username;
        public MemberDto(String username){
            this.username=username;
        }
    }
}
