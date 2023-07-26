package site.doggyyummy.gaebap.domain.pet.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PetRepository {
    private final EntityManager em;

    public void create(Pet pet){
        em.persist(pet);
    }
    public Pet selectOne (long id){
        Pet pet = em.createQuery("select distinct p from Pet p " +
                        "join fetch p.member m " +
                        "left join fetch p.forbiddens f " +
                        "where p.id = :id ",Pet.class)
                .setParameter("id" ,id)
                .getSingleResult();
        return pet;
    }
    public List<Pet> selectByMember (long memberId){
        List<Pet> pets = em.createQuery("select distinct p from Pet p " +
                        "join fetch p.member m " +
                        "left join fetch p.forbiddens f " +
                        "where p.member.id = :memberId ")
                .setParameter("memberId" ,memberId)
                .getResultList();
        return pets;
    }

    public void delete(Long id){
        Pet pet = em.find(Pet.class,id);
        if(pet!=null){
            em.remove(pet);
        }
    }

}
