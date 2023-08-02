package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import org.apache.http.HttpStatus;

@Getter
public class RecipeModifyResponseDto {
    //for exceptionHandling
    private int statusCode;
    private String errorMessage;
    public RecipeModifyResponseDto(int statusCode,String errorMessage){
        this.statusCode=statusCode;
        this.errorMessage=errorMessage;
    }
}
