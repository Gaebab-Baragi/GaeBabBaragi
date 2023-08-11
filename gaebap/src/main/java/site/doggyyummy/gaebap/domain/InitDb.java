package site.doggyyummy.gaebap.domain;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.meeting.entity.Status;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import site.doggyyummy.gaebap.domain.member.entity.Role;
import site.doggyyummy.gaebap.domain.member.service.MemberServiceImpl;

import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.RecipeIngredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Step;


import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class InitDb {

   private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final MemberServiceImpl memberService;

        public void dbInit1() throws Exception {
            String url = "http://localhost:3000/image/%EA%B0%9C%EB%B0%A5%EB%B0%94%EB%9D%BC%EA%B8%B0.png";

            Member member1 = createMember("test1@gmail.com","test1","박영서", url);
            Member member2 = createMember("test2@gmail.com","test2","user2", url);
            Member member3 = createMember("test3@gmail.com","test3","user3", url);

            memberService.signUp(member1);
            memberService.signUp(member2);
            memberService.signUp(member3);

            Ingredient ingredient1 = createIngredient("양파");
            Ingredient ingredient2=createIngredient("당근");
            Ingredient ingredient3=createIngredient("소고기");
            Ingredient ingredient4=createIngredient("가지");
            Ingredient ingredient5=createIngredient("닭고기");
            em.persist(ingredient1);
            em.persist(ingredient2);
            em.persist(ingredient3);
            em.persist(ingredient4);
            em.persist(ingredient5);

            Recipe recipe1=createRecipe("멍김밥",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            em.persist(recipe1);


            RecipeIngredient recipeIngredient1=createRecipeIngredient(ingredient1,recipe1,"1개");
            RecipeIngredient recipeIngredient2=createRecipeIngredient(ingredient2,recipe1,"0.5개");
            RecipeIngredient recipeIngredient3=createRecipeIngredient(ingredient3,recipe1,"100g");
            RecipeIngredient recipeIngredient4=createRecipeIngredient(ingredient4,recipe1,"2개");
            RecipeIngredient recipeIngredient5=createRecipeIngredient(ingredient5,recipe1,"150g");
            em.persist(recipeIngredient1);
            em.persist(recipeIngredient2);
            em.persist(recipeIngredient3);
            em.persist(recipeIngredient4);
            em.persist(recipeIngredient5);


            Step step1=createStep(recipe1,1L,"재료를 잘라주세요");
            Step step2=createStep(recipe1,2L,"모든 재료를 볶아주세요");
            Step step3=createStep(recipe1,3L,"플레이팅");
            em.persist(step1);
            em.persist(step2);
            em.persist(step3);


            Pet pet1 = createPet(1L,"지산",20.1D,LocalDateTime.now());
            Pet pet2 = createPet(1L,"토리",20.2D,LocalDateTime.now());
            Pet pet3 = createPet(1L,"아토",20.3D,LocalDateTime.now());
            em.persist(pet1);
            em.persist(pet2);
            em.persist(pet3);


            em.persist(createMeeting(null, 2, "1", "1", 1L, ZonedDateTime.now(ZoneId.of("Asia/Seoul")), 1L, Status.ATTENDEE_WAIT, 0));
            em.persist(createMeeting(null, 2, "2", "2", 1L, ZonedDateTime.now(ZoneId.of("Asia/Seoul")), 1L, Status.SCHEDULED,  0));

            Forbidden forbidden = createForbidden(1L,1L);
            em.persist(forbidden);
        }

        private RecipeIngredient createRecipeIngredient(Ingredient ingredient,Recipe recipe,String amount) {
            RecipeIngredient recipeIngredient=new RecipeIngredient();
            recipeIngredient.setAmount(amount);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setRecipe(recipe);
            List<RecipeIngredient> recipeIngredients=recipe.getRecipeIngredients();
            recipeIngredients.add(recipeIngredient);
            recipe.setRecipeIngredients(recipeIngredients);
            return recipeIngredient;
        }

        private Member createMember(String username, String password, String nickname, String profileUrl) {

            Member member = new Member();

            member.setNickname(nickname);
            member.setUsername(username);
            member.setRole(Role.USER);
            member.setPassword(password);
            member.setProfileUrl(profileUrl);

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
        private Step createStep(Recipe recipe,Long orderingNumber,String description){
            Step step=new Step();
            step.setRecipe(recipe);
            step.setOrderingNumber(orderingNumber);
            step.setDescription(description);
            List<Step> steps=recipe.getSteps();
            steps.add(step);
            recipe.setSteps(steps);
            step.setS3Url("http://localhost:3000/image/%EA%B0%9C%EB%B0%A5%EB%B0%94%EB%9D%BC%EA%B8%B0.png");
            return step;

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

        private Meeting createMeeting(String password, int max_participant, String title, String description, Long member_id, ZonedDateTime start_time, Long recipe_id, Status status, int currentParticipants) {
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

