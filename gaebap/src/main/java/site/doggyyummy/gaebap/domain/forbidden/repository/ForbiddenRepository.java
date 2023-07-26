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
    public Forbidden selectOne (long petId, long ingredientId){
        Forbidden forbidden = (Forbidden)em.createQuery("select f from Forbidden f" +
                        " where f.pet.id = :petId" +
                        " and f.ingredient.id = :ingredientId"
                )
                .setParameter("petId" ,petId)
                .setParameter("ingredientId" ,ingredientId)
                .getSingleResult();
        return forbidden;
    }

    public List<Forbidden> selectByPet (long petId){
        List<Forbidden> forbiddens = em.createQuery("select f from Forbidden f " +
                        "left join fetch f.ingredient i " +
                        "left join fetch f.pet p " +
                        "where f.pet.id = :petId")
                .setParameter("petId" ,petId)
                .getResultList();
        return forbiddens;
    }

    public void delete(Forbidden forbidden){
        em.createQuery("delete   Forbidden f" +
                " where f.pet.id = :petId" +
                " and f.ingredient.id = :ingredientId")
                .setParameter("petId" ,forbidden.getPet().getId())
                .setParameter("ingredientId" ,forbidden.getIngredient().getId())
                .executeUpdate();
    }
}
