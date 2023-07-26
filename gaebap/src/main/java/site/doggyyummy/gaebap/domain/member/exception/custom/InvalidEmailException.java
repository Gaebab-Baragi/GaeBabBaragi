package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(){
        super(ExceptionCode.INVALID_EMAIL_EXCEPTION.getErrorMessage());
    }

    public InvalidEmailException(String message){
        super(message);
    }
}
