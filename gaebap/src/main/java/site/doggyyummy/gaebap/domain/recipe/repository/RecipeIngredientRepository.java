package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Long> {
    RecipeIngredient findByIngredientIdAndRecipeId(Long ingredientId,Long recipeId);

}
