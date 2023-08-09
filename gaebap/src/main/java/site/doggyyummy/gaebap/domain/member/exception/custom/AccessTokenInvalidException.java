package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class AccessTokenInvalidException extends RuntimeException {
    public AccessTokenInvalidException() {
        super(ExceptionCode.ACCESS_TOKEN_INVALID_EXCEPTION.getErrorMessage());
    }

    public AccessTokenInvalidException(String message) {
        super(message);
    }
}
