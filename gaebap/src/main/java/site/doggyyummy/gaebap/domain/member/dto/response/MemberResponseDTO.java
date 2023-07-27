package site.doggyyummy.gaebap.domain.member.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.util.List;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberResponseDTO {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String profileUrl;
    private Meeting hostedMeeting;
    private String googleAccount;
    private String kakaoAccount;
    private String naverAccount;

    public static MemberResponseDTO toDTO(Member member){
        return MemberResponseDTO.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .nickname(member.getNickname())
                        .profileUrl(member.getProfileUrl())
                        .hostedMeeting(member.getHostedMeeting())
                        .email(member.getEmail())
                        .googleAccount(member.getGoogleAccount())
                        .kakaoAccount(member.getKakaoAccount())
                        .naverAccount(member.getNaverAccount())
                .build();
    }

}
