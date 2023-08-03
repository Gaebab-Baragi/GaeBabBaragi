package site.doggyyummy.gaebap.domain.meeting.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MeetingForbiddenException extends RuntimeException{

    private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

    public MeetingForbiddenException(String errorMessage) {
        super("미팅 리소스 접근 불가: " + errorMessage);
    }
}
