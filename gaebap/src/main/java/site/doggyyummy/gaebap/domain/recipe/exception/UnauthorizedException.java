package site.doggyyummy.gaebap.domain.recipe.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException{

    private final int statusCode;
    public UnauthorizedException(int statusCode,String errorMessage) {
        super(errorMessage);
        this.statusCode=statusCode;
    }

}
