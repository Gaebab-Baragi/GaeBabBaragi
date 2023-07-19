package site.doggyyummy.gaebap.domain.member.dto;

import lombok.AllArgsConstructor;
import site.doggyyummy.gaebap.domain.member.domain.Member;

@AllArgsConstructor
public class MemberRegisterDTO {
    private String registerName;
    private String password;
    private String nickname;
    private String email;

    public static Member toMember(MemberRegisterDTO registerDTO){
        Member member = new Member();
        member.setName(registerDTO.registerName);
        member.setEmail(registerDTO.email);
        member.setNickname(registerDTO.nickname);
        member.setPassword(registerDTO.password);
        return member;
    }
}
