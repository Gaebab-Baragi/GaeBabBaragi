package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class IngredientAllResponseDto {
    List<IngredientDto> ingredients;
    public IngredientAllResponseDto(List<Ingredient> ingredients){
        this.ingredients=ingredients.stream()
                .map(ingredient -> new IngredientDto(ingredient.getId(),ingredient.getName()))
                .collect(Collectors.toList());
    }
    @Getter
    public static class IngredientDto{
        private Long id;
        private String name;
        public IngredientDto(Long id,String name){
            this.id=id;
            this.name=name;
        }
    }
}
