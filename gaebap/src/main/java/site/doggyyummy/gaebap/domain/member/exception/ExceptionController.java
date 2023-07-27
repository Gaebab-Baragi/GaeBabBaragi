package site.doggyyummy.gaebap.domain.member.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.doggyyummy.gaebap.domain.member.exception.custom.*;

@RequiredArgsConstructor
@RestControllerAdvice("site.doggyyummy.gaebap")
public class ExceptionController {


    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException (DuplicateEmailException err){
        return getResponseEntity(ExceptionCode.DUPLICATE_EMAIL_EXCEPTION);
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<String> handleDuplicateNicknameException (DuplicateNicknameException err){
        return getResponseEntity(ExceptionCode.DUPLICATE_NICKNAME_EXCEPTION);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsernameException (DuplicateUsernameException err){
        return getResponseEntity(ExceptionCode.DUPLICATE_USERNAME_EXCEPTION);
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<String> handleExpiredRefreshTokenException (ExpiredRefreshTokenException err){
        return getResponseEntity(ExceptionCode.EXPIRED_REFRESH_TOKEN_EXCEPTION);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> handleIncorrectPasswordException(IncorrectPasswordException err){
        return getResponseEntity(ExceptionCode.INCORRECT_PASSWORD_EXCEPTION);
    }

    @ExceptionHandler(InvalidNameFormatException.class)
    public ResponseEntity<String> handleInvalidNameFormatException (InvalidNameFormatException err){
        return getResponseEntity(ExceptionCode.INVALID_NAME_FORMAT_EXCEPTION);
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    public ResponseEntity<String> handleInvalidPasswordFormatException (InvalidPasswordFormatException err){
        return getResponseEntity(ExceptionCode.INVALID_PASSWORD_FORMAT_EXCEPTION);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleIncorrectPasswordException(NoSuchUserException err){
        return getResponseEntity(ExceptionCode.NO_SUCH_USER_EXCEPTION);
    }

    @ExceptionHandler(NoSuchUsernameException.class)
    public ResponseEntity<String> handleIncorrectPasswordException(NoSuchUsernameException err){
        return getResponseEntity(ExceptionCode.NO_SUCH_USERNAME_EXCEPTION);
    }
    public static ResponseEntity<String> getResponseEntity(ExceptionCode code){
        return ResponseEntity
                .status(code.getErrorCode())
                .body(code.getErrorMessage());
    }
}
