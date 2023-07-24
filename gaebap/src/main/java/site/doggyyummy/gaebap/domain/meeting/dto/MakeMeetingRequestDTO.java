package site.doggyyummy.gaebap.domain.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MakeMeetingRequestDTO {

    private String password;

    private Long maxParticipant;

    private String title;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    // 레시피 기능 구현 후 추가
    // private Recipe recipe;

    private String sessionId;
}
