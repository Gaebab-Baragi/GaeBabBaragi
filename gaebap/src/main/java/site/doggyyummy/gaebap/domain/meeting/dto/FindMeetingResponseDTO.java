package site.doggyyummy.gaebap.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;

import java.time.ZonedDateTime;

@Getter @Setter
@Builder
public class FindMeetingResponseDTO {

    private Long id;

    @JsonProperty("max_participant")
    private int maxParticipant;

    private String title;

    private String description;

    @JsonProperty("host_nickname")
    private String hostNickname;

    @JsonProperty("host_profile_url")
    private String hostProfileUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @JsonProperty("start_time")
    private ZonedDateTime startTime;

    @JsonProperty("recipe_id")
    private Long recipeId;

    @JsonProperty("recipe_title")
    private String recipeTitle;

    @JsonProperty("recipe_image_url")
    private String recipeImageUrl;

    private Status status;

    @JsonProperty("current_participants")
    private int currentParticipants;

    public static FindMeetingResponseDTO toDTO(Meeting meeting) {
        FindMeetingResponseDTO findMeetingResponseDTO = FindMeetingResponseDTO.builder()
                .id(meeting.getId())
                .maxParticipant(meeting.getMaxParticipant())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .hostNickname(meeting.getHost().getNickname())
                .hostProfileUrl(meeting.getHost().getProfileUrl())
                .startTime(meeting.getStartTime())
                .recipeId(meeting.getRecipe().getId())
                .recipeTitle(meeting.getRecipe().getTitle())
                .recipeImageUrl(meeting.getRecipe().getImageUrl())
                .status(meeting.getStatus())
                .currentParticipants(meeting.getCurrentParticipants())
                .build();

        return findMeetingResponseDTO;
    }
}
