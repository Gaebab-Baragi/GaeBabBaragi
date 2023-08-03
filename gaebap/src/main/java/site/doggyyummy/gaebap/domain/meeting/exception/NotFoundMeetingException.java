package site.doggyyummy.gaebap.domain.meeting.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundMeetingException extends RuntimeException{

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public NotFoundMeetingException() {
        super("미팅 정보 오류: 미팅 정보를 찾을 수 없습니다.");
    }
}
