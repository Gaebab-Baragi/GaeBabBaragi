package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.member.entity.Member;

@Getter
@Setter
public class RecipeUploadResponseDto {
    private String title;
    private MemberDto member;
    //for exception
    private String errorMessage;
    private int statusCode;

    public RecipeUploadResponseDto(String title,Member member,int statusCode,String errorMessage){
        this.title=title;
        if(member==null){
            this.member=null;
        }else {
            this.member = new MemberDto(member.getUsername(), member.getId());
        }
        this.errorMessage=errorMessage;
        this.statusCode=statusCode;
    }
    public RecipeUploadResponseDto(String title, MemberDto member) {
        this.title = title;
        this.member=member;
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

