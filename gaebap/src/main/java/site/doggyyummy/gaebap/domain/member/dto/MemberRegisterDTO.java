package site.doggyyummy.gaebap.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.domain.Member;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class MemberRegisterDTO {
    private String registerName;
    private String password;
    private String nickname;
    private String email;

    public static Member toMember(MemberRegisterDTO registerDTO){
        Member member = new Member();

        member.setName(registerDTO.getRegisterName());
        member.setNickname(registerDTO.getNickname());
        member.setAuthority("READ");

        member.setEmail(registerDTO.getEmail());
        member.setPassword(registerDTO.getPassword());
        member.setRegisterDate(new Timestamp(System.currentTimeMillis()));

        System.out.println(member.toString());
        return member;
    }
}
