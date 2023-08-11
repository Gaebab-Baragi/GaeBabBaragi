package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecipePopularResponseDto {
    List<RecipeDto> popularRecipes;

    public RecipePopularResponseDto(List<Recipe> popularRecipes){
        this.popularRecipes=popularRecipes.stream()
                .map(recipe->new RecipePopularResponseDto.RecipeDto(recipe.getTitle(),recipe.getWrittenTime(),recipe.getHit(),recipe.getImageUrl(),recipe.getDescription(),recipe.getMember(),recipe.getId()))
                .collect(Collectors.toList());
    }

    @Getter
    public static class RecipeDto{
        private String title;
        private Long id;
        private RecipeSearchLikeResponseDto.MemberDto member;
        private LocalDateTime writtenTime;
        private Long hit;
        private String description;
        private String imgUrl;
        public RecipeDto(String title, LocalDateTime writtenTime, String imgUrl, Long hit, String description, RecipeSearchLikeResponseDto.MemberDto member, Long id){
            this.title=title;
            this.writtenTime=writtenTime;
            this.hit=hit;
            this.description=description;
            this.member=member;
            this.id=id;
            this.imgUrl=imgUrl;
        }
        public RecipeDto(String title, LocalDateTime writtenTime, Long hit, String imgUrl, String description, Member member, Long id){
            this.title=title;
            this.writtenTime=writtenTime;
            this.hit=hit;
            this.description=description;
            this.imgUrl=imgUrl;
            this.member=new RecipeSearchLikeResponseDto.MemberDto(member.getNickname());
            this.id=id;
        }

    }

    @Getter
    public static class MemberDto{
        private String nickname;
        public MemberDto(String nickname){
            this.nickname=nickname;
        }
    }
}
