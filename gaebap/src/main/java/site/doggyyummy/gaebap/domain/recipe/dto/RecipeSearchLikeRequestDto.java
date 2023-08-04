package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeSearchLikeRequestDto {
    private String title;
    private List<IngredientDto> ingredients;
    private List<PetDto> pets;
    @Getter
    public static class IngredientDto{
        private String name;
    }

    @Getter
    public static class PetDto{
        private Long id;
    }

}
