package site.doggyyummy.gaebap.domain.meeting.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MeetingEntryConditionNotMetException extends RuntimeException{

    private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

    public MeetingEntryConditionNotMetException(String errorMessage) {
        super("미팅 입장 불가: " + errorMessage);
    }
}
