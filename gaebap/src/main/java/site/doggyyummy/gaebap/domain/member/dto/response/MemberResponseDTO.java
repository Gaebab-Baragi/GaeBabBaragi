package site.doggyyummy.gaebap.domain.member.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.doggyyummy.gaebap.domain.member.entity.Member;

@AllArgsConstructor
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberResponseDTO {

    private String username;
    private String nickname;
    private String email;

    public static MemberResponseDTO toDTO(Member member){
        return new MemberResponseDTO(member.getUsername(), member.getNickname(), member.getEmail());
    }

}
