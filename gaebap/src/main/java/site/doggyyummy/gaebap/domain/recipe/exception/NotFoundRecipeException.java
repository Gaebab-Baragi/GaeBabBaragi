package site.doggyyummy.gaebap.domain.recipe.exception;

import lombok.Getter;

@Getter
public class NotFoundRecipeException extends RuntimeException{
    private final int statusCode;

    public NotFoundRecipeException(int statusCode,String errorMessage){
        super(errorMessage);
        this.statusCode=statusCode;
    }
}
