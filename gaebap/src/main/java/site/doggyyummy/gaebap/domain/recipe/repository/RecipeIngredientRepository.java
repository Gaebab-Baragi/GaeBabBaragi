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
    //postgre쿼리
    @Query("SELECT COUNT(ri), ri.recipe.id " +
            "FROM RecipeIngredient ri " +
            "WHERE ri.recipe.title LIKE '%' || :recipeTitle || '%' " +
            "AND ri.recipe.id IN ("+
            "SELECT r.id "+
            "FROM RecipeIngredient ri JOIN ri.recipe r " +
            "WHERE r.id NOT IN (" +
            "SELECT r2.id "+
            "FROM RecipeIngredient ri2 JOIN ri2.recipe r2 JOIN ri2.ingredient i2 " +
            "WHERE i2.id IN (SELECT f.ingredient.id FROM Forbidden f WHERE f.pet.id IN :petId))) "+
            "GROUP BY ri.recipe.id ")

//    h2 쿼리문
//    @Query("SELECT COUNT(ri), ri.recipe.id " +
//            "FROM RecipeIngredient ri " +
//            "WHERE ri.recipe.title LIKE %:recipeTitle% " +
//            "AND ri.recipe.id IN ("+
//            "SELECT r.id "+
//            "FROM RecipeIngredient ri JOIN ri.recipe r " +
//            "WHERE r.id NOT IN (" +
//            "SELECT r2.id "+
//            "FROM RecipeIngredient ri2 JOIN ri2.recipe r2 JOIN ri2.ingredient i2 " +
//            "WHERE i2.id IN (SELECT f.ingredient.id FROM Forbidden f WHERE f.pet.id IN :petId))) "+
//            "GROUP BY ri.recipe.id ")
    List<Object[]> findRecipesWithForbiddenIngredientsAndTitle(@Param("petId") List<Long> petId, @Param("recipeTitle") String recipeTitle);


    //재료만 있는 경우
    //postgre 쿼리
    @Query("SELECT COUNT(ri), ri.recipe.id " +
            "FROM RecipeIngredient ri " +
            "WHERE ri.ingredient.name IN :ingredientNames " +
            "AND ri.recipe.title LIKE '%' || :recipeTitle || '%' " +
            "GROUP BY ri.recipe.id " +
            "HAVING COUNT(ri) >=:ingredientSize")
    //h2 쿼리
//    @Query("SELECT COUNT(ri), ri.recipe.id " +
//            "FROM RecipeIngredient ri " +
//            "WHERE ri.ingredient.name IN :ingredientNames " +
//            "AND ri.recipe.title LIKE %:recipeTitle% " +
//            "GROUP BY ri.recipe.id " +
//            "HAVING COUNT(ri) >=:ingredientSize")
    List<Object[]> findRecipesWithIngredientsAndTitle(@Param("ingredientNames") List<String> ingredientNames, @Param("recipeTitle") String recipeTitle,@Param("ingredientSize") Integer ingredientSize);

    //postgre 쿼리
    @Query("SELECT COUNT(ri), ri.recipe.id " +
            "FROM RecipeIngredient ri " +
            "WHERE ri.ingredient.name IN :ingredientNames " +
            "AND ri.recipe.title LIKE '%' || :recipeTitle || '%' " +
            "AND ri.recipe.id IN ("+
            "SELECT r.id "+
            "FROM RecipeIngredient ri JOIN ri.recipe r " +
            "WHERE r.id NOT IN (" +
            "SELECT r2.id "+
            "FROM RecipeIngredient ri2 JOIN ri2.recipe r2 JOIN ri2.ingredient i2 " +
            "WHERE i2.id IN (SELECT f.ingredient.id FROM Forbidden f WHERE f.pet.id IN :petId))) "+
            "GROUP BY ri.recipe.id "+
            "HAVING COUNT(ri) >= :ingredientSize")
    //h2쿼리
//    @Query("SELECT COUNT(ri), ri.recipe.id " +
//            "FROM RecipeIngredient ri " +
//            "WHERE ri.ingredient.name IN :ingredientNames " +
//            "AND ri.recipe.title LIKE %:recipeTitle% " +
//            "AND ri.recipe.id IN ("+
//            "SELECT r.id "+
//            "FROM RecipeIngredient ri JOIN ri.recipe r " +
//            "WHERE r.id NOT IN (" +
//            "SELECT r2.id "+
//            "FROM RecipeIngredient ri2 JOIN ri2.recipe r2 JOIN ri2.ingredient i2 " +
//            "WHERE i2.id IN (SELECT f.ingredient.id FROM Forbidden f WHERE f.pet.id IN :petId))) "+
//            "GROUP BY ri.recipe.id "+
//            "HAVING COUNT(ri) >= :ingredientSize")
    List<Object[]> findRecipesWithIngredientsAndTitleAndForbiddenIngredients(@Param("ingredientNames") List<String> ingredientNames, @Param("recipeTitle") String recipeTitle, @Param("ingredientSize") Integer ingredientSize, @Param("petId") List<Long> petId);

}

