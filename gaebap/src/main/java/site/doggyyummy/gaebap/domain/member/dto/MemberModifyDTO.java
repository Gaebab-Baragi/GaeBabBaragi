package site.doggyyummy.gaebap.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.sql.Timestamp;

@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
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
