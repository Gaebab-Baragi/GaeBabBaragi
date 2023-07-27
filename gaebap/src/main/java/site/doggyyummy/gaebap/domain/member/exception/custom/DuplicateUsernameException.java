package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(){
        super(ExceptionCode.DUPLICATE_USERNAME_EXCEPTION.getErrorMessage());
    }

    public DuplicateUsernameException(String message){
        super(message);
    }
}
