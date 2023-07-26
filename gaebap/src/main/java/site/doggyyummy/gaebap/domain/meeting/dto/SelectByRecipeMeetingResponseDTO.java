package site.doggyyummy.gaebap.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class SelectByRecipeMeetingResponseDTO {

    private Long id;

    @JsonProperty("max_participant")
    private Long maxParticipant;

    private String title;

    @JsonProperty("host_nickname")
    private String hostNickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    private Status status;

    public static SelectByRecipeMeetingResponseDTO toDTO(Meeting meeting) {
        SelectByRecipeMeetingResponseDTO selectByRecipeMeetingResponseDTO = SelectByRecipeMeetingResponseDTO.builder()
                .id(meeting.getId())
                .maxParticipant(meeting.getMaxParticipant())
                .title(meeting.getTitle())
                .hostNickname(meeting.getHost().getNickname())
                .startTime(meeting.getStartTime())
                .status(meeting.getStatus())
                .build();

        return selectByRecipeMeetingResponseDTO;
    }
}
