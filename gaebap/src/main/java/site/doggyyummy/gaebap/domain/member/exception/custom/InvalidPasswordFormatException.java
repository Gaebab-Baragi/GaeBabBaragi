package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class InvalidPasswordFormatException extends RuntimeException{
    public InvalidPasswordFormatException(){
        super(ExceptionCode.INVALID_PASSWORD_FORMAT_EXCEPTION.getErrorMessage());
    }

    public InvalidPasswordFormatException(String message){
        super(message);
    }
}
