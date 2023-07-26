package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.doggyyummy.gaebap.domain.recipe.entity.Step;

public interface StepRepository extends JpaRepository<Step,Long> {

    Step findByRecipeIdAndOrderingNumber(Long recipeId,Long orderingNumber);

}
