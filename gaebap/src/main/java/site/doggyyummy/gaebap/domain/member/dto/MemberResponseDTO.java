package site.doggyyummy.gaebap.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import site.doggyyummy.gaebap.domain.member.entity.Member;

@AllArgsConstructor
@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberResponseDTO {

    private String username;
    private String nickname;
    private String email;

    public static MemberResponseDTO toDTO(Member member){
        return new MemberResponseDTO(member.getName(), member.getNickname(), member.getEmail());
    }

}
