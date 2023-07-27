package site.doggyyummy.gaebap.domain.member.exception.custom;

import site.doggyyummy.gaebap.domain.member.exception.ExceptionCode;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException() {
        super(ExceptionCode.DUPLICATE_EMAIL_EXCEPTION.getErrorMessage());
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
