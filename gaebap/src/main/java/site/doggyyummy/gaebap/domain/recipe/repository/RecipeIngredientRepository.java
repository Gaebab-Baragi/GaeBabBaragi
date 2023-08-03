package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Long> {
    RecipeIngredient findByIngredientIdAndRecipeId(Long ingredientId,Long recipeId);
    //펫id만 있는 경우
    @Query("SELECT COUNT(ri), ri.recipe.id " +
            "FROM RecipeIngredient ri " +
            "WHERE ri.recipe.title LIKE %:recipeTitle% " +
            "AND ri.recipe.id IN ("+
            "SELECT ri.recipe.id "+
            "FROM ri WHERE ri.recipe.id "+
            "NOT IN (SELECT ri.recipe.id "+
            "FROM ri WHERE ri.ingredient.id "+
            "IN (SELECT f.ingredient.id FROM Forbidden f WHERE f.pet.id IN :petId))) "+
            "GROUP BY ri.recipe.id ")
    List<Object[]> findRecipesWithForbiddenIngredientsAndTitle(@Param("petId") List<Long> petId, @Param("recipeTitle") String recipeTitle);


    //재료만 있는 경우
    @Query("SELECT COUNT(ri), ri.recipe.id " +
            "FROM RecipeIngredient ri " +
            "WHERE ri.ingredient.name IN :ingredientNames " +
            "AND ri.recipe.title LIKE %:recipeTitle% " +
            "GROUP BY ri.recipe.id " +
            "HAVING COUNT(ri) >=:ingredientSize")
    List<Object[]> findRecipesWithIngredientsAndTitle(@Param("ingredientNames") List<String> ingredientNames, @Param("recipeTitle") String recipeTitle,@Param("ingredientSize") Integer ingredientSize);
    @Query("SELECT COUNT(ri), ri.recipe.id " +
            "FROM RecipeIngredient ri " +
            "WHERE ri.ingredient.name IN :ingredientNames " +
            "AND ri.recipe.title LIKE %:recipeTitle% " +
            "AND ri.ingredient.id NOT IN (SELECT f.ingredient.id FROM Forbidden f WHERE f.pet.id IN :petId) "+
            "GROUP BY ri.recipe.id " +
            "HAVING COUNT(ri) >= :ingredientSize")
    List<Object[]> findRecipesWithIngredientsAndTitleAndForbiddenIngredients(@Param("ingredientNames") List<String> ingredientNames, @Param("recipeTitle") String recipeTitle, @Param("ingredientSize") Integer ingredientSize, @Param("petId") List<Long> petId);

}

