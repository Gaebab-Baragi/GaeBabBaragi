package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class ExpiredRefreshTokenException extends RuntimeException {
    public ExpiredRefreshTokenException(){
        super(ExceptionCode.EXPIRED_REFRESH_TOKEN_EXCEPTION.getErrorMessage());
    }

    public ExpiredRefreshTokenException(String message){
        super(message);
    }
}
