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
public class ModifyMeetingRequestDTO {

    private Long id;

    private String password;

    @JsonProperty("max_participant")
    private Long maxParticipant;

    private String title;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    public Meeting toEntity() {

        Meeting meeting = Meeting.builder()
                .id(this.id)
                .password(this.password)
                .maxParticipant(this.maxParticipant)
                .title(this.title)
                .description(this.description)
                .startTime(this.startTime)
                .status(Status.SCHEDULED)
                .build();

        return meeting;
    }
}
