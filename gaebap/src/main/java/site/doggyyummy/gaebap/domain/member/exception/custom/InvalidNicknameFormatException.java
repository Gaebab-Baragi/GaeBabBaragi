package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class InvalidNicknameFormatException extends RuntimeException{

    public InvalidNicknameFormatException(){
        super(ExceptionCode.INVALID_NICKNAME_FORMANT_EXCEPTION.getErrorMessage());
    }

    public InvalidNicknameFormatException(String message){
        super(message);
    }
}
