package site.doggyyummy.gaebap.domain;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
            Member member2 = createMember("test2@gmail.com","test2","김선형", url);
            Member member3 = createMember("test3@gmail.com","test3","유승아", url);

            memberService.signUp(member1);
            memberService.signUp(member2);
            memberService.signUp(member3);

            Ingredient ingredient1 = createIngredient("양파");
            Ingredient ingredient2=createIngredient("당근");
            Ingredient ingredient3=createIngredient("소고기");
            Ingredient ingredient4=createIngredient("가지");
            Ingredient ingredient5=createIngredient("닭고기");
            Ingredient ingredient6=createIngredient("사과");
            Ingredient ingredient7=createIngredient("배");
            Ingredient ingredient8=createIngredient("계란");
            em.persist(ingredient1);
            em.persist(ingredient2);
            em.persist(ingredient3);
            em.persist(ingredient4);
            em.persist(ingredient5);
            em.persist(ingredient6);
            em.persist(ingredient7);
            em.persist(ingredient8);

            Recipe recipe1=createRecipe("멍김밥1",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe2=createRecipe("멍김밥2",member2,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe3=createRecipe("멍김밥3",member2,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe4=createRecipe("멍김밥4",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe5=createRecipe("멍김밥5",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe6=createRecipe("멍김밥6",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe7=createRecipe("멍김밥7",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe8=createRecipe("멍김밥8",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe9=createRecipe("멍김밥9",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe10=createRecipe("멍김밥10",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe11=createRecipe("멍김밥11",member2,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe12=createRecipe("멍김밥12",member2,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe13=createRecipe("멍김밥13",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe14=createRecipe("멍김밥14",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());
            Recipe recipe15=createRecipe("멍김밥15",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
                    url,LocalDateTime.now());

            em.persist(recipe1);
            em.persist(recipe2);
            em.persist(recipe3);
            em.persist(recipe4);
            em.persist(recipe5);
            em.persist(recipe6);
            em.persist(recipe7);
            em.persist(recipe8);
            em.persist(recipe9);
            em.persist(recipe10);
            em.persist(recipe11);
            em.persist(recipe12);
            em.persist(recipe13);
            em.persist(recipe14);
            em.persist(recipe15);


            RecipeIngredient recipeIngredient1=createRecipeIngredient(ingredient1,recipe1,"1개");
            RecipeIngredient recipeIngredient2=createRecipeIngredient(ingredient2,recipe1,"0.5개");
            RecipeIngredient recipeIngredient3=createRecipeIngredient(ingredient3,recipe1,"100g");
            RecipeIngredient recipeIngredient4=createRecipeIngredient(ingredient4,recipe1,"2개");
            RecipeIngredient recipeIngredient5=createRecipeIngredient(ingredient5,recipe1,"150g");
            RecipeIngredient recipeIngredient6=createRecipeIngredient(ingredient5,recipe2,"150g");
            RecipeIngredient recipeIngredient7=createRecipeIngredient(ingredient6,recipe2,"2개");
            RecipeIngredient recipeIngredient8=createRecipeIngredient(ingredient7,recipe3,"3개");
            RecipeIngredient recipeIngredient9=createRecipeIngredient(ingredient8,recipe4,"0.5개");
            RecipeIngredient recipeIngredient10=createRecipeIngredient(ingredient1,recipe5,"1개");
            RecipeIngredient recipeIngredient11=createRecipeIngredient(ingredient3,recipe6,"120g");
            RecipeIngredient recipeIngredient12=createRecipeIngredient(ingredient5,recipe2,"150g");
            RecipeIngredient recipeIngredient13=createRecipeIngredient(ingredient6,recipe7,"2개");
            RecipeIngredient recipeIngredient14=createRecipeIngredient(ingredient7,recipe8,"3개");
            RecipeIngredient recipeIngredient15=createRecipeIngredient(ingredient8,recipe9,"0.5개");
            RecipeIngredient recipeIngredient16=createRecipeIngredient(ingredient1,recipe9,"1개");
            RecipeIngredient recipeIngredient17=createRecipeIngredient(ingredient3,recipe6,"120g");
            RecipeIngredient recipeIngredient18=createRecipeIngredient(ingredient5,recipe2,"150g");
            RecipeIngredient recipeIngredient19=createRecipeIngredient(ingredient6,recipe2,"2개");
            RecipeIngredient recipeIngredient20=createRecipeIngredient(ingredient7,recipe3,"3게");
            RecipeIngredient recipeIngredient21=createRecipeIngredient(ingredient8,recipe4,"0.5개");
            RecipeIngredient recipeIngredient22=createRecipeIngredient(ingredient1,recipe5,"1개");
            RecipeIngredient recipeIngredient23=createRecipeIngredient(ingredient3,recipe6,"120g");
            RecipeIngredient recipeIngredient24=createRecipeIngredient(ingredient3,recipe10,"120g");
            RecipeIngredient recipeIngredient25=createRecipeIngredient(ingredient5,recipe10,"150g");
            RecipeIngredient recipeIngredient26=createRecipeIngredient(ingredient6,recipe11,"2개");
            RecipeIngredient recipeIngredient27=createRecipeIngredient(ingredient7,recipe12,"3개");
            RecipeIngredient recipeIngredient28=createRecipeIngredient(ingredient8,recipe12,"0.5개");
            RecipeIngredient recipeIngredient29=createRecipeIngredient(ingredient1,recipe13,"1개");
            RecipeIngredient recipeIngredient30=createRecipeIngredient(ingredient1,recipe14,"1개");
            RecipeIngredient recipeIngredient31=createRecipeIngredient(ingredient3,recipe14,"120g");
            RecipeIngredient recipeIngredient32=createRecipeIngredient(ingredient3,recipe15,"120g");

            em.persist(recipeIngredient1);
            em.persist(recipeIngredient2);
            em.persist(recipeIngredient3);
            em.persist(recipeIngredient4);
            em.persist(recipeIngredient5);
            em.persist(recipeIngredient6);
            em.persist(recipeIngredient7);
            em.persist(recipeIngredient8);
            em.persist(recipeIngredient9);
            em.persist(recipeIngredient10);
            em.persist(recipeIngredient11);
            em.persist(recipeIngredient12);
            em.persist(recipeIngredient13);
            em.persist(recipeIngredient14);
            em.persist(recipeIngredient15);
            em.persist(recipeIngredient16);
            em.persist(recipeIngredient17);
            em.persist(recipeIngredient18);
            em.persist(recipeIngredient19);
            em.persist(recipeIngredient20);
            em.persist(recipeIngredient21);
            em.persist(recipeIngredient22);
            em.persist(recipeIngredient23);
            em.persist(recipeIngredient24);
            em.persist(recipeIngredient25);
            em.persist(recipeIngredient26);
            em.persist(recipeIngredient27);
            em.persist(recipeIngredient28);
            em.persist(recipeIngredient29);
            em.persist(recipeIngredient30);
            em.persist(recipeIngredient31);
            em.persist(recipeIngredient32);


            Step step1=createStep(recipe1,1L,"재료를 잘라주세요");
            Step step2=createStep(recipe1,2L,"모든 재료를 볶아주세요");
            Step step3=createStep(recipe1,3L,"플레이팅");
            Step step4=createStep(recipe2,1L,"재료를 잘라주세요");
            Step step5=createStep(recipe3,1L,"재료를 잘라주세요");
            Step step6=createStep(recipe4,1L,"재료를 잘라주세요");
            Step step7=createStep(recipe5,1L,"재료를 잘라주세요");
            Step step8=createStep(recipe6,1L,"재료를 잘라주세요");
            Step step9=createStep(recipe7,1L,"재료를 잘라주세요");
            Step step10=createStep(recipe8,1L,"재료를 잘라주세요");
            Step step11=createStep(recipe9,1L,"재료를 잘라주세요");
            Step step12=createStep(recipe10,1L,"재료를 잘라주세요");
            Step step13=createStep(recipe11,1L,"재료를 잘라주세요");
            Step step14=createStep(recipe12,1L,"재료를 잘라주세요");
            Step step15=createStep(recipe13,1L,"재료를 잘라주세요");
            Step step16=createStep(recipe14,1L,"재료를 잘라주세요");
            Step step17=createStep(recipe15,1L,"재료를 잘라주세요");

            em.persist(step1);
            em.persist(step2);
            em.persist(step3);
            em.persist(step4);
            em.persist(step5);
            em.persist(step6);
            em.persist(step7);
            em.persist(step8);
            em.persist(step9);
            em.persist(step10);
            em.persist(step11);
            em.persist(step12);
            em.persist(step13);
            em.persist(step14);
            em.persist(step15);
            em.persist(step16);
            em.persist(step17);


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