package site.doggyyummy.gaebap.domain.meeting.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidArgumentMeetingCreateException extends RuntimeException{

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public InvalidArgumentMeetingCreateException(String errorMessage) {
        super("미팅 예약 오류: " + errorMessage);
    }
}
