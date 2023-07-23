package site.doggyyummy.gaebap.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class MemberModifyDTO {

    private String modifyName;
    private String password;
    private String nickname;
    private String email;

    public static Member toEntity(MemberModifyDTO registerDTO){
        Member member = new Member();

        member.setName(registerDTO.getModifyName());
        member.setNickname(registerDTO.getNickname());
        member.setEmail(registerDTO.getEmail());
        member.setPassword(registerDTO.getPassword());
        member.setRegisterDate(new Timestamp(System.currentTimeMillis()));

        return member;
    }
}
