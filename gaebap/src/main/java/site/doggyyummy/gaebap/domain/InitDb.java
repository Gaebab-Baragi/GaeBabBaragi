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
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;


import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
            String url = "https://sh-bucket.s3.ap-northeast-2.amazonaws.com/image/1/7517bd2d-fedb-4fd6-b153-530fc95d07cc-teayang.jpg";
            Member member1 = createMember("배찬일","배찬일","user",url,new Timestamp(System.currentTimeMillis()));
            Member member2 = createMember("김선형","김선형","user",url,new Timestamp(System.currentTimeMillis()));
            Member member3 = createMember("유승아","유승아","user",url,new Timestamp(System.currentTimeMillis()));
            Member member4 = createMember("박영서","박박영서","user",url,new Timestamp(System.currentTimeMillis()));
            Member member5 = createMember("김하늘","김하늘","user",url,new Timestamp(System.currentTimeMillis()));
            Member member6 = createMember("박준형","박준형","user",url,new Timestamp(System.currentTimeMillis()));
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);
            em.persist(member6);

            Ingredient ingredient1 = createIngredient("양파");
            Ingredient ingredient2=createIngredient("당근");
            Ingredient ingredient3=createIngredient("소고기");
            em.persist(ingredient1);
            em.persist(ingredient2);
            em.persist(ingredient3);

//            RecipeIngredient recipeIngredient1=createRecipeIngredient(ingredient1,"1개");
//            RecipeIngredient recipeIngredient2=createRecipeIngredient(ingredient2,"2개");
//            RecipeIngredient recipeIngredient3=createRecipeIngredient(ingredient3,"100g");
//
//
//            List<RecipeIngredient> recipeIngredientList1=new ArrayList<>();
//            recipeIngredientList1.add(recipeIngredient1);
//            List<RecipeIngredient> recipeIngredientList2=new ArrayList<>();
//            recipeIngredientList2.add(recipeIngredient1);
//            recipeIngredientList2.add(recipeIngredient2);
//
//            List<RecipeIngredient> recipeIngredientList3=new ArrayList<>();
//            recipeIngredientList3.add(recipeIngredient1);
//            recipeIngredientList3.add(recipeIngredient2);
//            recipeIngredientList3.add(recipeIngredient3);
//            for(RecipeIngredient ri:recipeIngredientList1){
//                em.persist(ri);
//            }
//            for(RecipeIngredient ri:recipeIngredientList2){
//                em.persist(ri);
//            }
//            for(RecipeIngredient ri:recipeIngredientList3){
//                em.persist(ri);
//            }

//            Recipe recipe1 = createRecipe("제목1",member1,"레시피입니다1",
//                    url,
//                    LocalDateTime.now(),recipeIngredientList1);
//            em.persist(recipe1);
//            Recipe recipe2 = createRecipe("제목2",member1,"레시피입니다2",
//                    url,
//                    LocalDateTime.now(),recipeIngredientList2);
//            em.persist(recipe2);
//            Recipe recipe3 = createRecipe("제목3",member1,"레시피입니다3",
//                    url,
//                    LocalDateTime.now(),recipeIngredientList3);
//            em.persist(recipe3);
            Recipe recipe1 = createRecipe("제목1",member1,"레시피입니다1",
                    url,
                    LocalDateTime.now());
            Recipe recipe2 = createRecipe("제목2",member1,"레시피입니다2",
                    url,
                    LocalDateTime.now());
            Recipe recipe3 = createRecipe("제목3",member1,"레시피입니다3",
                    url,
                    LocalDateTime.now());
            em.persist(recipe1);
            em.persist(recipe2);
            em.persist(recipe3);



            Pet pet1 = createPet(1L,"지산",20.1D,LocalDateTime.now());
            Pet pet2 = createPet(1L,"토리",20.2D,LocalDateTime.now());
            Pet pet3 = createPet(1L,"아토",20.3D,LocalDateTime.now());
            em.persist(pet1);
            em.persist(pet2);
            em.persist(pet3);


            em.persist(createMeeting(null, 2, "1", "1", 1L, LocalDateTime.of(2023, 7, 27, 15, 10), 1L, Status.ATTENDEE_WAIT, 0));
            em.persist(createMeeting(null, 2, "2", "2", 2L, LocalDateTime.of(2023, 7, 26, 18, 0), 1L, Status.SCHEDULED,  0));
            em.persist(createMeeting(null, 2, "3", "3", 3L, LocalDateTime.of(2023, 7, 26, 15, 0), 2L, Status.SCHEDULED, 0));
            em.persist(createMeeting(null, 2, "4", "4", 4L, LocalDateTime.of(2023, 7, 26, 15, 0), 2L, Status.SCHEDULED, 0));
            em.persist(createMeeting(null, 2, "5", "5", 5L, LocalDateTime.of(2023, 7, 29, 15, 0), 2L, Status.SCHEDULED, 0));

            Forbidden forbidden = createForbidden(1L,1L);
            em.persist(forbidden);
        }

        private RecipeIngredient createRecipeIngredient(Ingredient ingredient,String amount) {
            RecipeIngredient recipeIngredient=new RecipeIngredient();
            recipeIngredient.setAmount(amount);
            recipeIngredient.setIngredient(ingredient);
            return recipeIngredient;
        }

        private Member createMember(String name, String nickName, String authority,
                                    String profileUrl, Timestamp registerDate) {

            Member member = new Member();
            member.setNickname(nickName);
            member.setUsername(name);
            member.setAuthority(authority);
            member.setProfileUrl(profileUrl);
            member.setRegisterDate(registerDate);

            return member;
        }
        private Recipe createRecipe(String title, Member member, String description, String imgageUrl, LocalDateTime nowTime) {
            Recipe recipe = new Recipe();
            recipe.setTitle(title);
            recipe.setMember(member);
            recipe.setDescription(description);
            recipe.setImageUrl(imgageUrl);
            recipe.setWrittenTime(nowTime);
            return recipe;
        }
        private Recipe createRecipe(String title, Member member, String description, String imgageUrl, LocalDateTime nowTime, List<RecipeIngredient> recipeIngredients) {
            Recipe recipe = new Recipe();
            recipe.setTitle(title);
            recipe.setMember(member);
            recipe.setDescription(description);
            recipe.setImageUrl(imgageUrl);
            recipe.setWrittenTime(nowTime);
            recipe.setRecipeIngredients(recipeIngredients);
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

