package site.doggyyummy.gaebap.domain.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Address;

public interface TestRepository extends JpaRepository<Address,Long> {
}