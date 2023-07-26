package site.doggyyummy.gaebap.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class MakeMeetingResponseDTO {

    private Long id;

    private String password;

    @JsonProperty("max_participant")
    private Long maxParticipant;

    private String title;

    private String description;

    @JsonProperty("member_id")
    private Long hostId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("recipe_id")
    private Long recipeId;
}
