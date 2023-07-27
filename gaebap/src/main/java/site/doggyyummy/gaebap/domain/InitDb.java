package site.doggyyummy.gaebap.domain;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;
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
            Member member1 = createMember("배찬일");
            Member member2 = createMember("김선형");
            Member member3 = createMember("유승아");
            Member member4 = createMember("박영서");
            Member member5 = createMember("김하늘");
            Member member6 = createMember("박준형");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
            em.persist(member6);

            Recipe recipe1 = createRecipe("제목1",member1);
            Recipe recipe2 = createRecipe("제목2",member1);
            Recipe recipe3 = createRecipe("제목3",member1);
            em.persist(recipe1);
            em.persist(recipe2);
            em.persist(recipe3);

            Pet pet1 = createPet(1L,"배찬일",20.1D,LocalDateTime.now());
            Pet pet2 = createPet(1L,"배찬일",20.2D,LocalDateTime.now());
            Pet pet3 = createPet(1L,"배찬일",20.3D,LocalDateTime.now());
            em.persist(pet1);
            em.persist(pet2);
            em.persist(pet3);

            Ingredient ingredient = createIngredient("양파");
            em.persist(ingredient);

            em.persist(createMeeting(null, 2, "1", "1", 1L, LocalDateTime.of(2023, 7, 27, 15, 10), 1L, Status.ATTENDEE_WAIT, 0));
            em.persist(createMeeting(null, 2, "2", "2", 2L, LocalDateTime.of(2023, 7, 26, 18, 0), 1L, Status.SCHEDULED,  0));
            em.persist(createMeeting(null, 2, "3", "3", 3L, LocalDateTime.of(2023, 7, 26, 15, 0), 2L, Status.SCHEDULED, 0));
            em.persist(createMeeting(null, 2, "4", "4", 4L, LocalDateTime.of(2023, 7, 26, 15, 0), 2L, Status.SCHEDULED, 0));
            em.persist(createMeeting(null, 2, "5", "5", 5L, LocalDateTime.of(2023, 7, 29, 15, 0), 2L, Status.SCHEDULED, 0));

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

        private Meeting createMeeting(String password, int max_participant, String title, String description, Long member_id, LocalDateTime start_time, Long recipe_id, Status status, int currentParticipants) {
            Member member = new Member();
            member.setId(member_id);

            Recipe recipe = new Recipe();
            recipe.setId(recipe_id);

            Meeting meeting = Meeting.builder()
                    .password(password)
                    .maxParticipant(max_participant)
                    .title(title)
                    .description(description)
                    .host(member)
                    .startTime(start_time)
                    .recipe(recipe)
                    .status(status)
                    .currentParticipants(currentParticipants)
                    .build();

            return meeting;
        }
    }
}

