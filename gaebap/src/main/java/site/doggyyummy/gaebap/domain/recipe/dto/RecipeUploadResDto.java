package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.member.entity.Member;

@Getter
@Setter
public class RecipeUploadResDto {
    private String title;
    private MemberDto member;

    public RecipeUploadResDto(String title,MemberDto member) {
        this.title = title;
        this.member=member;

    }
    public RecipeUploadResDto(String title, Member member){
        this.title=title;
        this.member=new MemberDto(member.getName(),member.getId());
    }

    @Getter
    public static class MemberDto{
        private String name;
        private Long id;
        public MemberDto(String name,Long id) {
            this.name = name;
            this.id=id;
        }
    }

}

