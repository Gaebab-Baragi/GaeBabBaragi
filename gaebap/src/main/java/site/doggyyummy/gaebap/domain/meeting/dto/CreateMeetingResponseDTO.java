package site.doggyyummy.gaebap.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;

import java.time.LocalDateTime;

@Getter @Builder
public class CreateMeetingResponseDTO implements ResponseDTO{

    private Long id;

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

    public static CreateMeetingResponseDTO toDTO(Meeting meeting) {
        CreateMeetingResponseDTO createMeetingResponseDTO = CreateMeetingResponseDTO.builder()
                .id(meeting.getId())
                .password(meeting.getPassword())
                .maxParticipant(meeting.getMaxParticipant())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .hostId(meeting.getHost().getId())
                .startTime(meeting.getStartTime())
                .recipeId(meeting.getRecipe().getId())
                .build();

        return createMeetingResponseDTO;
    }
}
