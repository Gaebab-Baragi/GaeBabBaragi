package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Long> {
    RecipeIngredient findByIngredientIdAndRecipeId(Long ingredientId,Long recipeId);

    @Query("SELECT COUNT(ri), ri.recipe.id " +
            "FROM RecipeIngredient ri " +
            "WHERE ri.ingredient.name IN :ingredientNames " +
            "AND ri.recipe.title LIKE %:recipeTitle% " +
            "GROUP BY ri.recipe.id " +
            "HAVING COUNT(ri) >=:ingredientSize")
    List<Object[]> findRecipesWithIngredientsAndTitle(@Param("ingredientNames") List<String> ingredientNames, @Param("recipeTitle") String recipeTitle,@Param("ingredientSize") Integer ingredientSize);
}

