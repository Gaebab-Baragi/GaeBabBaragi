package site.doggyyummy.gaebap.domain;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("배찬일");
            em.persist(member);

            Recipe recipe = createRecipe("제목1",member);
            em.persist(recipe);

            Pet pet1 = createPet(1L,"배찬일",20.1D,LocalDateTime.now());
            Pet pet2 = createPet(1L,"배찬일",20.2D,LocalDateTime.now());
            Pet pet3 = createPet(1L,"배찬일",20.3D,LocalDateTime.now());
            em.persist(pet1);
            em.persist(pet2);
            em.persist(pet3);

            Ingredient ingredient = createIngredient("양파");
            em.persist(ingredient);

/*            Forbidden forbidden = createForbidden(1L,1L);
            em.persist(forbidden);*/
        }
        private Member createMember(String name) {
            Member member = new Member();
            member.setName(name);

            return member;
        }
        private Recipe createRecipe(String title, Member member) {
            Recipe recipe = new Recipe();
            recipe.setTitle(title);
            recipe.setMember(member);

            return recipe;
        }
        private Pet createPet(Long memberId, String name, Double weight, LocalDateTime birthdate){
            Pet pet = new Pet();
            Member member = new Member();
            member.setId(memberId);
            pet.setMember(member);
            pet.setName(name);
            pet.setWeight(weight);
            pet.setBirthDate(birthdate);
            return pet;
        }

        private Forbidden createForbidden(Long petId, Long ingredientId){
            Forbidden forbidden = new Forbidden();
            Pet pet = new Pet();
            pet.setId(petId);
            forbidden.setPet(pet);
            Ingredient ingredient = new Ingredient();
            ingredient.setId(ingredientId);
            forbidden.setIngredient(ingredient);
            return forbidden;
        }

        private Ingredient createIngredient(String name){
            Ingredient ingredient = new Ingredient();
            ingredient.setName(name);
            return ingredient;
        }

    }
}

