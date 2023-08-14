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
        this.name=member.getNickname();
        this.recipes=recipes.stream()
                .map(recipe -> new RecipeFindByMemberIdResponseDto.RecipeDto(recipe.getId(),recipe.getTitle(),recipe.getDescription(),recipe.getWrittenTime(),recipe.getHit(),recipe.getImageUrl(),recipe.getMember()))
                .collect(Collectors.toList());
    }
    @Getter
    public static class MemberDto{
        private String nickname;
        public MemberDto(String nickname){
            this.nickname=nickname;
        }
    }
    @Getter
    public static class RecipeDto{
        private Long id;
        private String title;
        private String description;
        private LocalDateTime writtenTime;
        private Long hit;
        private String imgUrl;
        private MemberDto member;
        public RecipeDto(Long id,String title,String description,LocalDateTime writtenTime,Long hit,String imgUrl,Member member){
            this.id=id;
            this.title=title;
            this.description=description;
            this.writtenTime=writtenTime;
            this.hit=hit;
            this.imgUrl=imgUrl;
            this.member=new MemberDto(member.getNickname());
        }
    }
}
