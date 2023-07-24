package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeUploadReqDto {
    private String title;
    private String description;
    private MemberDto member;

    @Getter
    public static class MemberDto{
        private String name;
    }
}