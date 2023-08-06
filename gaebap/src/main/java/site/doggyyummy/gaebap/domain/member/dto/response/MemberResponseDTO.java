package site.doggyyummy.gaebap.domain.member.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberResponseDTO {

    private Long id;
    private String username;
    private String nickname;
    private String profileUrl;
    private List<Meeting> hostedMeeting;


    public static MemberResponseDTO toDTO(Member member){
        return MemberResponseDTO.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .nickname(member.getNickname())
                        .profileUrl(member.getProfileUrl())
                        .hostedMeeting(member.getHostedMeeting())
                .build();
    }

}
