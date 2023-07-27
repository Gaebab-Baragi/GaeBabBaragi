package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.util.List;
import java.util.stream.Collectors;

//레시피의 제목, 작성자 이름만
@Getter
@Setter
public class RecipeAllResponseDto {
    private List<RecipeDto> recipes;
    public RecipeAllResponseDto(List<Recipe> recipes){
        this.recipes=recipes.stream()
                .map(recipe -> new RecipeDto(recipe.getTitle(),recipe.getMember()))
                .collect(Collectors.toList());
    }

    @Getter
    public static class RecipeDto{
        private String title;
        private MemberDto member;
        public RecipeDto(String title,MemberDto member){
            this.title=title;
            this.member=member;
        }
        public RecipeDto(String title,Member member){
            this.title=title;
            this.member=new MemberDto(member.getUsername());
        }
    }

    @Getter
    public static class MemberDto{
        private String name;
        public MemberDto(String name){
            this.name=name;
        }
    }

}
