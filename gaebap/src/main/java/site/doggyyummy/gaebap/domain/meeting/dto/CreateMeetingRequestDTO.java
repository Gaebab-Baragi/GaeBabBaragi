package site.doggyyummy.gaebap.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class CreateMeetingRequestDTO {

    private String password;

    @JsonProperty("max_participant")
    private int maxParticipant;

    private String title;

    private String description;

    @JsonProperty("member_id")
    private Long hostId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("recipe_id")
    private Long recipeId;

    public Meeting toEntity() {
        // 호스트 멤버
        Member host = new Member();
        host.setId(this.hostId);

        // 레시피 정보
        Recipe recipe = new Recipe();
        recipe.setId(this.recipeId);

        Meeting meeting = Meeting.builder()
                .password(this.password)
                .maxParticipant(this.maxParticipant)
                .title(this.title)
                .description(this.description)
                .host(host)
                .startTime(this.startTime)
                .recipe(recipe)
                .status(Status.SCHEDULED)
                .currentParticipants(0)
                .build();

        return meeting;
    }
}
