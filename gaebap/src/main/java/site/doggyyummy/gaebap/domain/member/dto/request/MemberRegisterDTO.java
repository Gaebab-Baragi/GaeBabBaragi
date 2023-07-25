package site.doggyyummy.gaebap.domain.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberRegisterDTO {

    private String registerName;
    private String password;
    private String nickname;
    private String email;

    public static Member toEntity(MemberRegisterDTO registerDTO){
        Member member = new Member();

        member.setName(registerDTO.getRegisterName());
        member.setNickname(registerDTO.getNickname());
        member.setAuthority("ROLE_USER");//추후 변경 필요

        member.setEmail(registerDTO.getEmail());
        member.setPassword(registerDTO.getPassword());
        member.setRegisterDate(new Timestamp(System.currentTimeMillis()));

        return member;
    }

    @Override
    public String toString() {
        return "MemberRegisterDTO{" +
                "registerName='" + registerName + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
