package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecipeSearchLikeResponseDto {
    private List<RecipeDto> recipes;
    public RecipeSearchLikeResponseDto(List<Recipe> recipes){
        this.recipes=recipes.stream()
                .map(recipe->new RecipeDto(recipe.getTitle(),recipe.getWrittenTime(),recipe.getHit(),recipe.getDescription(),recipe.getMember(),recipe.getId()))
                .collect(Collectors.toList());
    }
    @Getter
    public static class RecipeDto {
        private String title;
        private Long id;
        private MemberDto member;
        private LocalDateTime writtenTime;
        private Long hit;
        private String description;

        public RecipeDto(String title,LocalDateTime writtenTime,Long hit,String description,MemberDto member,Long id){
            this.title=title;
            this.writtenTime=writtenTime;
            this.hit=hit;
            this.description=description;
            this.member=member;
            this.id=id;
        }
        public RecipeDto(String title,LocalDateTime writtenTime,Long hit,String description, Member member,Long id){
            this.title=title;
            this.writtenTime=writtenTime;
            this.hit=hit;
            this.description=description;
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
