package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    Ingredient findByName(String name);


}
