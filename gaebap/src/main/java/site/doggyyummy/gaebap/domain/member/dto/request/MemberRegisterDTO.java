package site.doggyyummy.gaebap.domain.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.entity.Role;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberRegisterDTO {

    private String registerName;
    private String password;
    private String nickname;

    public static Member toEntity(MemberRegisterDTO registerDTO){
        Member member = Member.builder()
                .username(registerDTO.getRegisterName())
                .nickname(registerDTO.getNickname())
                .password(registerDTO.getPassword())
                .role(Role.USER)
                .registerDate(new Timestamp(System.currentTimeMillis()))
                .build();

        return member;
    }
}
