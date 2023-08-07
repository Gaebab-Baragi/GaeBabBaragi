package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//레시피의 제목, 작성자 이름만
@Getter
@Setter
public class RecipeAllResponseDto {
    private List<RecipeDto> recipes;
    public RecipeAllResponseDto(List<Recipe> recipes){
        this.recipes=recipes.stream()
                .map(recipe -> new RecipeDto(recipe.getTitle(),recipe.getHit(),recipe.getWrittenTime(),recipe.getDescription(),recipe.getImageUrl(),recipe.getMember()))
                .collect(Collectors.toList());
    }

    @Getter
    public static class RecipeDto{
        private String title;
        private MemberDto member;
        private LocalDateTime writtenTime;
        private String description;
        private String recipeImageUrl;
        private Long hit;

        public RecipeDto(String title,Long hit,LocalDateTime writtenTime,String description,String recipeImageUrl,MemberDto member){
            this.title=title;
            this.hit=hit;
            this.writtenTime=writtenTime;
            this.description=description;
            this.recipeImageUrl=recipeImageUrl;
            this.member=member;
        }
        public RecipeDto(String title,Long hit,LocalDateTime writtenTime,String description,String recipeImageUrl,Member member){
            this.title=title;
            this.hit=hit;
            this.member=new MemberDto(member.getUsername());
            this.writtenTime=writtenTime;
            this.description=description;
            this.recipeImageUrl=recipeImageUrl;
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
