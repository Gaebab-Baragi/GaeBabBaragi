package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
}
