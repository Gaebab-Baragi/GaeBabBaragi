package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(){
        super(ExceptionCode.INCORRECT_PASSWORD_EXCEPTION.getErrorMessage());
    }
    public IncorrectPasswordException(String message){
        super(message);
    }
}
