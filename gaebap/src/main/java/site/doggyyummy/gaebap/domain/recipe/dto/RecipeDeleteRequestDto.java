package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;

@Getter
public class RecipeDeleteRequestDto {
    private MemberDto loginMember;

    @Getter
    public static class MemberDto{
        private Long id;
    }
}
