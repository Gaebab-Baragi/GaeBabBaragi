package site.doggyyummy.gaebap.domain.member.dto.request;

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

    public static Member toEntity(MemberModifyDTO modifyDTO){
        Member member = Member
                .builder()
                .username(modifyDTO.getModifyName())
                .nickname(modifyDTO.getModifyName())
                .password(modifyDTO.getPassword())
                .build();

        return member;
    }
}
