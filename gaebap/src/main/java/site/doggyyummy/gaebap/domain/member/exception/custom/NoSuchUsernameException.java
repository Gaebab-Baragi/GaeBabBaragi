package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class NoSuchUsernameException extends Exception {
    public NoSuchUsernameException(){
        super(ExceptionCode.NO_SUCH_USERNAME_EXCEPTION.getErrorMessage());
    }

    public NoSuchUsernameException(String message){
        super(message);
    }
}
