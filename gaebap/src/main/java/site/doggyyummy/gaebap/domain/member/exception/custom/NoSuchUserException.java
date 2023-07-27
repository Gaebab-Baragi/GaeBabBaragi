package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class NoSuchUserException extends RuntimeException{

    public NoSuchUserException(){
        super(ExceptionCode.NO_SUCH_USERNAME_EXCEPTION.getErrorMessage());
    }

    public NoSuchUserException(String message){
        super(message);
    }
}
