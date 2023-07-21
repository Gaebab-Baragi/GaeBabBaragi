package site.doggyyummy.gaebap.domain.member.dto;

import lombok.AllArgsConstructor;
import site.doggyyummy.gaebap.domain.member.entity.Member;

@AllArgsConstructor
public class MemberResponseDTO {

    private String name;
    private String nickname;
    private String email;

    public static MemberResponseDTO toDTO(Member member){
        return new MemberResponseDTO(member.getName(), member.getNickname(), member.getEmail());
    }

}
