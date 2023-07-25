package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    List<Recipe> findByMemberId(Long member_id);
    @Modifying(clearAutomatically = true)
    @Query("update Recipe r set r.title=:title where r.id=:id")
    int update(Long id, String title);

    @Modifying(clearAutomatically = true)
    @Query("update Recipe r set r.hit=r.hit+1 where r.id=:id")
    int addHits(Long id);
}
