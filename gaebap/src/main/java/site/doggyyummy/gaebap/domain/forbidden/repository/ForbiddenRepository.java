package site.doggyyummy.gaebap.domain.forbidden.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ForbiddenRepository {
    private final EntityManager em;

    public void create(Forbidden forbidden){
        em.persist(forbidden);
    }
    public Forbidden selectOne (long pet_id, long ingredient_id){
        Forbidden forbidden = (Forbidden)em.createQuery("select f from Forbidden f" +
                        " where f.pet.id = :pet_id" +
                        " and f.ingredient.id = :ingredient_id"
                )
                .setParameter("pet_id" ,pet_id)
                .setParameter("ingredient_id" ,ingredient_id)
                .getSingleResult();
        return forbidden;
    }

    public List<Forbidden> selectByPet (long pet_id){
        List<Forbidden> forbiddens = em.createQuery("select f from Forbidden f" +
                        " where f.pet.id = :pet_id")
                .setParameter("pet_id" ,pet_id)
                .getResultList();
        return forbiddens;
    }

    public void delete(long pet_id, long ingredient_id){
        em.createQuery("delete   Forbidden f" +
                " where f.pet.id = :pet_id" +
                " and f.ingredient.id = :ingredient_id")
                .setParameter("pet_id" ,pet_id)
                .setParameter("ingredient_id" ,ingredient_id)
                .executeUpdate();
    }

}
