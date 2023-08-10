package site.doggyyummy.gaebap.domain.member.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    NO_SUCH_USERNAME_EXCEPTION(452, "존재하지 않는 계정입니다."),
    INCORRECT_PASSWORD_EXCEPTION(453, "잘못된 비밀번호입니다."),
    INVALID_EMAIL_EXCEPTION(454, "잘못된 이메일입니다."),
    DUPLICATE_EMAIL_EXCEPTION(455, "이미 사용 중인 이메일입니다."),
    DUPLICATE_USERNAME_EXCEPTION(456, "이미 사용 중인 아이디입니다."),//안 씀
    DUPLICATE_NICKNAME_EXCEPTION(457, "이미 사용 중인 닉네임입니다."),
    EXPIRED_REFRESH_TOKEN_EXCEPTION(458, "갱신 토큰이 만료되었습니다."),
    INVALID_NAME_FORMAT_EXCEPTION(459, "잘못된 아이디 형식입니다."),//안 씀
    INVALID_NICKNAME_FORMANT_EXCEPTION(460, "잘못된 닉네임 형식입니다.."),
    INVALID_PASSWORD_FORMAT_EXCEPTION(461, "잘못된 패스워드 형식입니다."),
    NO_SUCH_USER_EXCEPTION(462,"존재하지 않는 회원입니다."),
    FILE_SIZE_LIMIT_EXCEEDED_EXCEPTION(463, "파일의 크기가 너무 큽니다(최대 1megabyte)"),
    ACCESS_TOKEN_INVALID_EXCEPTION(458, "액세스 토큰이 잘못 되었음");


    private final Integer errorCode;
    private final String errorMessage;

    ExceptionCode(Integer errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


}
