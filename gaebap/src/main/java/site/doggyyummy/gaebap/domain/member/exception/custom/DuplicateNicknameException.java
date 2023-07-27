package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class DuplicateNicknameException extends RuntimeException{
    public DuplicateNicknameException(){
        super(ExceptionCode.DUPLICATE_NICKNAME_EXCEPTION.getErrorMessage());
    }

    public DuplicateNicknameException(String message){
        super(message);
    }
}
