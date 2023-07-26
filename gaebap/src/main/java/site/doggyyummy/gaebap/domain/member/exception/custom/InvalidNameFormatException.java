package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class InvalidNameFormatException extends RuntimeException{
    public InvalidNameFormatException(){
        super(ExceptionCode.INVALID_NAME_FORMAT_EXCEPTION.getErrorMessage());
    }

    public InvalidNameFormatException(String message){
        super(message);
    }
}
