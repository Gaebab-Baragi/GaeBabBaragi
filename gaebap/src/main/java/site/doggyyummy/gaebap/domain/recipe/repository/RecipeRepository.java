package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}
