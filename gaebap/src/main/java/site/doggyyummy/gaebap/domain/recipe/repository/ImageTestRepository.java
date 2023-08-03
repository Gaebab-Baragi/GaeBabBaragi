package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Image;

public interface ImageTestRepository extends JpaRepository<Image,Long> {
}
