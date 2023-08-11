package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RecipeTitleAllResponseDto {
    private List<String> titles;

    public RecipeTitleAllResponseDto(List<String> titles){
        this.titles=titles;
    }
}
