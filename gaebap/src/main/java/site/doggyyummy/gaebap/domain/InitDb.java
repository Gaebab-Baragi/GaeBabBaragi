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

            Ingredient ingredient1=createIngredient("사과");
            Ingredient ingredient2=createIngredient("바나나");
            Ingredient ingredient3=createIngredient("청경채");
            Ingredient ingredient4=createIngredient("브로콜리");
            Ingredient ingredient5=createIngredient("양배추");
            Ingredient ingredient6=createIngredient("당근");
            Ingredient ingredient7=createIngredient("닭 가슴살");
            Ingredient ingredient8=createIngredient("계란");
            Ingredient ingredient9=createIngredient("마늘");
            Ingredient ingredient10=createIngredient("포도");
            Ingredient ingredient11=createIngredient("우유");
            Ingredient ingredient12=createIngredient("양파");
            Ingredient ingredient13=createIngredient("감자");
            Ingredient ingredient14=createIngredient("호박");
            Ingredient ingredient15=createIngredient("시금치");
            Ingredient ingredient16=createIngredient("고구마");
            Ingredient ingredient17=createIngredient("두부");


            Ingredient ingredient18=createIngredient("올리브 오일");
            Ingredient ingredient19=createIngredient("플레인 요거트");
            Ingredient ingredient21=createIngredient("다진 닭고기");
            Ingredient ingredient22=createIngredient("현미밥");
            Ingredient ingredient23=createIngredient("그린빈");
            Ingredient ingredient24=createIngredient("마요네즈");
            Ingredient ingredient25=createIngredient("배");
            Ingredient ingredient26=createIngredient("소고기");
            Ingredient ingredient27=createIngredient("돼지고기");
            Ingredient ingredient28=createIngredient("통밀가루");
            Ingredient ingredient29=createIngredient("연어");
            Ingredient ingredient30=createIngredient("오트밀 가루");
            Ingredient ingredient31=createIngredient("크림치즈");
            Ingredient ingredient32=createIngredient("꿀");
            Ingredient ingredient33=createIngredient("사과즙");
            Ingredient ingredient34=createIngredient("베이킹파우더");
            Ingredient ingredient35=createIngredient("코코넛 가루");
            Ingredient ingredient36=createIngredient("식물성 오일");


            em.persist(ingredient1);
            em.persist(ingredient2);
            em.persist(ingredient3);
            em.persist(ingredient4);
            em.persist(ingredient5);
            em.persist(ingredient6);
            em.persist(ingredient7);
            em.persist(ingredient8);
            em.persist(ingredient9);
            em.persist(ingredient10);
            em.persist(ingredient11);
            em.persist(ingredient12);
            em.persist(ingredient13);
            em.persist(ingredient14);
            em.persist(ingredient15);
            em.persist(ingredient16);
            em.persist(ingredient17);
            em.persist(ingredient18);
            em.persist(ingredient19);
            em.persist(ingredient21);
            em.persist(ingredient22);
            em.persist(ingredient23);
            em.persist(ingredient24);
            em.persist(ingredient25);
            em.persist(ingredient26);
            em.persist(ingredient27);
            em.persist(ingredient28);
            em.persist(ingredient29);
            em.persist(ingredient30);
            em.persist(ingredient31);
            em.persist(ingredient32);
            em.persist(ingredient33);
            em.persist(ingredient34);
            em.persist(ingredient35);
            em.persist(ingredient36);






            Recipe recipe1=createRecipe("닭고기 채소 정식",member1,"강아지에게 필요한 단백질이 듬뿍 들어간 그린빈과 함께 다양한 건강한 재료들로 만드는 정식!",
                    url,LocalDateTime.now());
            em.persist(recipe1);
            RecipeIngredient recipeIngredient1=createRecipeIngredient(ingredient18,recipe1,"1 tbsp");
            RecipeIngredient recipeIngredient2=createRecipeIngredient(ingredient19,recipe1,"2 tbsp");
            RecipeIngredient recipeIngredient3=createRecipeIngredient(ingredient13,recipe1,"100g");
            RecipeIngredient recipeIngredient4=createRecipeIngredient(ingredient21,recipe1,"100g");
            RecipeIngredient recipeIngredient5=createRecipeIngredient(ingredient22,recipe1,"0.5g");
            RecipeIngredient recipeIngredient6=createRecipeIngredient(ingredient23,recipe1,"50g");
            RecipeIngredient recipeIngredient7=createRecipeIngredient(ingredient14,recipe1,"50g");
            em.persist(recipeIngredient1);
            em.persist(recipeIngredient2);
            em.persist(recipeIngredient3);
            em.persist(recipeIngredient4);
            em.persist(recipeIngredient5);
            em.persist(recipeIngredient6);
            em.persist(recipeIngredient7);

            Step step1=createStep(recipe1,1L,"몰드에 현미밥을 눌러 담고 강아지 그릇에 올려주세요.");
            Step step2=createStep(recipe1,2L,"감자 껍질을 벗기고 큼지막하게 썰어주세요.");
            Step step3=createStep(recipe1,3L,"물 2컵이 담긴 냄비에 감자를 넣고 화씨 350도에서 끓여주세요.");
            Step step4=createStep(recipe1,4L,"물을 끓이는 동안 그린빈 줄기를 제거하고 얇게 썰어주세요.");
            Step step5=createStep(recipe1,5L,"애호박 줄기를 제거하고 얇게 썰어주세요.");
            Step step6=createStep(recipe1,6L,"냄비에 그린빈을 넣고 약불(화씨 175도)로 줄이고 뚜껑을 닫고 5분 동안 삶아주세요.");
            Step step7=createStep(recipe1,7L,"냄비에서 그린빈을 건지고 애호박을 넣고 5분 동안 끓여주세요.");
            Step step8=createStep(recipe1,8L,"냄비에서 애호박과 감자를 건져 물기를 빼고 식혀주세요.");
            Step step9=createStep(recipe1,9L,"냄비에 올리브 오일을 두르고 중불(화씨 350도)에서 가열 후 닭고기를 넣고 5-7분 동안 찢어주면서 볶아주세요.");
            Step step10=createStep(recipe1,10L,"감자를 삶은 뒤 포크로 잘 으깨 공 모양 몰드에 눌러 담아 강아지 그릇에 올려주세요.");
            Step step11=createStep(recipe1,11L,"애호박 조각을 꽃 모양처럼 돌돌 말아주세요.");
            Step step12=createStep(recipe1,12L,"남은 조각을 강아지 그릇의 밑바닥에 놓고 그린빈을 올려주세요.");
            Step step13=createStep(recipe1,13L,"그릇의 가운데에 닭고기를 넣고 애호박 고명을 올려주세요.");
            Step step14=createStep(recipe1,14L,"요거트를 부어주세요.");
            Step step15=createStep(recipe1,15L,"급여해주세요.");
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

            Recipe recipe2=createRecipe("두부 간식",member1,"알러지가 있는 아이들, 방광 결석을 앓고 있는 아이들에게 최적의 간식 두부로 만드는 간식!",
                    url,LocalDateTime.now());
            em.persist(recipe2);
            RecipeIngredient recipeIngredient21=createRecipeIngredient(ingredient17,recipe2,"50g");
            em.persist(recipeIngredient21);

            Step step21=createStep(recipe2,1L,"두부를 물에 1시간 30분 담근 후 20분 정도 끓는 물에 넣어 간수를 빼주세요.");
            Step step22=createStep(recipe2,3L,"키친타올로 물기를 제거해주세요.");
            Step step23=createStep(recipe2,4L,"한입 크기로 잘라주세요.");
            Step step24=createStep(recipe2,5L,"전자레인지에 넣고 50초 돌리고 30초 식히고 8번 반복해주세요.");
            Step step25=createStep(recipe2,6L,"급여해주세요.");

            em.persist(step21);
            em.persist(step22);
            em.persist(step23);
            em.persist(step24);
            em.persist(step25);


            Recipe recipe3=createRecipe("바나나 오트밀 쿠키",member1,"바나나와 오트밀 단 2가지로 만들어보는 강아지 쿠키 만들기!",
                    url,LocalDateTime.now());
            em.persist(recipe3);
            RecipeIngredient recipeIngredient31=createRecipeIngredient(ingredient2,recipe3,"");
            em.persist(recipeIngredient31);

            Step step31=createStep(recipe3,1L,"바나나를 으깬 후 바나나 자체 수분으로 오트밀이 불을 때까지 5분 정도 기다려주세요.");
            Step step32=createStep(recipe3,2L,"혼합물을 숟가락으로 떠서 납작하게 쿠키 모양으로 만들어주세요.");
            Step step33=createStep(recipe3,3L,"에어프라이어에 180도에서 10분 정도 구워주세요.");
            Step step34=createStep(recipe3,4L,"급여해주세요.");

            em.persist(step31);
            em.persist(step32);
            em.persist(step33);
            em.persist(step34);




            Recipe recipe4=createRecipe("단호박 쿠키",member1,"오븐이 필요 없는 건강한 단호박으로 만드는 간단 단호박 쿠키 만들기!",
                    url,LocalDateTime.now());
            em.persist(recipe4);
            RecipeIngredient recipeIngredient41=createRecipeIngredient(ingredient14,recipe4,"");
            RecipeIngredient recipeIngredient42=createRecipeIngredient(ingredient8,recipe4,"");
            RecipeIngredient recipeIngredient43=createRecipeIngredient(ingredient6,recipe4,"");
            em.persist(recipeIngredient41);
            em.persist(recipeIngredient42);
            em.persist(recipeIngredient43);
            Step step41=createStep(recipe1,1L,"단호박을 깨끗하게 씻고 씨를 다 파내 준 다음 썰어줍니다.");
            Step step42=createStep(recipe1,2L,"전자레인지에 7-8분 돌려 삶아주세요.");
            Step step43=createStep(recipe1,3L,"호박 껍질을 숟가락을 사용해 분리해주세요.");
            Step step44=createStep(recipe1,4L,"호박을 으깬 후 계란 노른자, 쌀가루를 넣고 반죽을 만들어주세요.");
            Step step45=createStep(recipe1,5L,"식감을 위해 당근을 썰어 넣어주세요.");
            Step step46=createStep(recipe1,6L,"");
            Step step47=createStep(recipe1,7L,"반죽을 30분 정도 냉장고에 숙성해 주세요.");
            Step step48=createStep(recipe1,8L,"반죽을 밀대로 펴주세요.");
            Step step49=createStep(recipe1,9L,"쿠키틀로 모양을 내 반죽을 잘라주세요.");
            Step step50=createStep(recipe1,10L,"종이 호일을 깔고 프라이팬에 약불로 처음 8-10분, 뒤집어서 4-5분 구워주세요.");
            Step step51=createStep(recipe1,11L,"급여해주세요.");
            em.persist(step41);
            em.persist(step42);
            em.persist(step43);
            em.persist(step44);
            em.persist(step45);
            em.persist(step46);
            em.persist(step47);
            em.persist(step48);
            em.persist(step49);
            em.persist(step50);
            em.persist(step51);

            Recipe recipe5=createRecipe("아기 강아지들을 위한 - 강아지 이유식",member1,"생후 3주~4주의 아기 강아지들을 위한 맛있는 이유식 만들어보기!",
                    url,LocalDateTime.now());
            em.persist(recipe5);
            RecipeIngredient recipeIngredient51=createRecipeIngredient(ingredient11,recipe5,"1 cup");
            RecipeIngredient recipeIngredient52=createRecipeIngredient(ingredient8,recipe5,"1개");
            RecipeIngredient recipeIngredient53=createRecipeIngredient(ingredient19,recipe5,"1 cup");
            RecipeIngredient recipeIngredient54=createRecipeIngredient(ingredient24,recipe5,"1 tbsp");

            em.persist(recipeIngredient51);
            em.persist(recipeIngredient52);
            em.persist(recipeIngredient53);
            em.persist(recipeIngredient54);

            Step step57=createStep(recipe5,1L,"달걀 흰자와 노른자를 분리해주세요.");
            Step step52=createStep(recipe5,2L,"그릇에 달걀 노른자를 붓고 마요네즈를 넣어주세요.");
            Step step53=createStep(recipe5,3L,"거품기로 잘 풀어주세요.");
            Step step54=createStep(recipe5,4L,"염소 우유와 플레인 요거트를 조금씩 넣으면서 풀어주세요.");
            Step step55=createStep(recipe5,5L,"그릇이나 접시에 부어 급여해주세요.");

            em.persist(step52);
            em.persist(step53);
            em.persist(step54);
            em.persist(step55);
            em.persist(step57);

            Recipe recipe6=createRecipe("",member1,"",
                    url,LocalDateTime.now());
            em.persist(recipe6);
            RecipeIngredient recipeIngredient61=createRecipeIngredient(ingredient6,recipe6,"50g");
            RecipeIngredient recipeIngredient62=createRecipeIngredient(ingredient23,recipe6,"50g");
            RecipeIngredient recipeIngredient63=createRecipeIngredient(ingredient25,recipe6,"100g");
            RecipeIngredient recipeIngredient64=createRecipeIngredient(ingredient26,recipe6,"50g");

            em.persist(recipeIngredient61);
            em.persist(recipeIngredient62);
            em.persist(recipeIngredient63);
            em.persist(recipeIngredient64);

            Step step61=createStep(recipe6,1L,"그린빈을 잘게 썰어주세요.");
            Step step62=createStep(recipe6,2L,"당근 줄기를 제거하고 껍질을 벗겨 얇게 썰어주세요.");
            Step step63=createStep(recipe6,3L,"케서롤 그릇의 밑바닥에 당근 조각을 올려주세요.");
            Step step64=createStep(recipe6,4L,"그린빈과 소고기를 올리고 살짝 눌러주세요.");
            Step step65=createStep(recipe6,5L,"화씨 350도에서 25분 동안 구워주세요.");
            Step step66=createStep(recipe6,6L,"굽는 동안 배를 2/3정도 반으로 잘라주고 숟가락으로 배 속을 파내서 그릇처럼 만들어 줍니다. ※ 배의 가운데 부분과 씨앗은 꼭 제거해주세요 ※");
            Step step67=createStep(recipe6,7L,"배가 담긴 그릇에 소고기 채소 혼합물을 부어주세요.");
            Step step68=createStep(recipe6,8L,"완전히 식힌 후 급여해주세요. ※ 배의 껍질은 잘 씻어주시고 어린 강아지라면 껍질까지 벗긴 후 과육 위주로 급여해주세요. ※");

            em.persist(step61);
            em.persist(step62);
            em.persist(step63);
            em.persist(step64);
            em.persist(step65);
            em.persist(step66);
            em.persist(step67);
            em.persist(step68);

            Recipe recipe7=createRecipe("연어 쿠키",member1,"연어와 각종 채소로 만들어보는 맛있는 쿠키!",
                    url,LocalDateTime.now());
            em.persist(recipe7);
            RecipeIngredient recipeIngredient71=createRecipeIngredient(ingredient28,recipe7,"1 cup");
            RecipeIngredient recipeIngredient72=createRecipeIngredient(ingredient19,recipe7,"1 cup");
            RecipeIngredient recipeIngredient73=createRecipeIngredient(ingredient29,recipe7,"70 g");

            em.persist(recipeIngredient71);
            em.persist(recipeIngredient72);
            em.persist(recipeIngredient73);

            Step step71=createStep(recipe7,1L,"큰 그릇에 통밀가루를 담아주세요.");
            Step step72=createStep(recipe7,2L,"연어를 넣고 잘 으깨주세요.");
            Step step73=createStep(recipe7,3L,"그릇에 요거트를 부어주세요.");
            Step step74=createStep(recipe7,4L,"재료들이 잘 혼합되도록 골고루 섞어 혼합물이 반죽이 되도록 골고루 손으로 치대주세요.");
            Step step75=createStep(recipe7,5L,"유산지 위에 반죽을 올리고 밀대로 반죽을 1cm 두께로 밀어주세요.");
            Step step76=createStep(recipe7,6L,"쿠키틀로 반죽을 잘라주세요.");
            Step step77=createStep(recipe7,7L,"베이킹 시트 위에 올리고 화씨 325도에서 30-40분 동안 구워주세요.");
            Step step78=createStep(recipe7,8L,"음식을 완전히 식힌 후 급여해주세요.");

            em.persist(step71);
            em.persist(step72);
            em.persist(step73);
            em.persist(step74);
            em.persist(step75);
            em.persist(step76);
            em.persist(step77);
            em.persist(step78);

            Recipe recipe8=createRecipe("닭고기 츄",member1,"대량으로 만들어 보관해두었다가 그때 그때 줄 수 있는 닭고기 츄! 3가지 재료 만으로 30분 만에 만들 수 있는 닭고기 츄 만들기!",
                    url,LocalDateTime.now());
            em.persist(recipe8);
            RecipeIngredient recipeIngredient81=createRecipeIngredient(ingredient11,recipe8,"");
            RecipeIngredient recipeIngredient82=createRecipeIngredient(ingredient21,recipe8,"");
            RecipeIngredient recipeIngredient83=createRecipeIngredient(ingredient30,recipe8,"");

            em.persist(recipeIngredient81);
            em.persist(recipeIngredient82);
            em.persist(recipeIngredient83);

            Step step81=createStep(recipe8,1L,"큰 그릇에 닭고기를 넣어주세요.");
            Step step82=createStep(recipe8,2L,"오트밀 가루와 우유 가루를 넣어주세요.");
            Step step83=createStep(recipe8,3L,"재료들이 잘 혼합되도록 골고루 섞어주세요.");
            Step step84=createStep(recipe8,4L,"혼합물을 작은 공 모양으로 만들어서 오븐용 그릇에 넣어주세요.");
            Step step85=createStep(recipe8,5L,"화씨 350도에서 15-25분 동안 구워주세요.");
            Step step86=createStep(recipe8,6L,"완전히 식힌 후 급여해주세요.");

            em.persist(step81);
            em.persist(step82);
            em.persist(step83);
            em.persist(step84);
            em.persist(step85);
            em.persist(step86);

            Recipe recipe9=createRecipe("크림치즈 쿠키",member1,"한입 크기로 먹기 좋은 댕댕이 크림치즈 쿠키 만들기!",
                    url,LocalDateTime.now());
            em.persist(recipe9);
            RecipeIngredient recipeIngredient91=createRecipeIngredient(ingredient28,recipe9,"1cup");
            RecipeIngredient recipeIngredient92=createRecipeIngredient(ingredient31,recipe9,"1/4 cup");
            RecipeIngredient recipeIngredient93=createRecipeIngredient(ingredient32,recipe9,"1 tbsp");
            RecipeIngredient recipeIngredient94=createRecipeIngredient(ingredient33,recipe9,"2 tbsp");
            RecipeIngredient recipeIngredient95=createRecipeIngredient(ingredient8,recipe9,"1 개");
            RecipeIngredient recipeIngredient96=createRecipeIngredient(ingredient34,recipe9,"1/2 tbsp");

            em.persist(recipeIngredient91);
            em.persist(recipeIngredient92);
            em.persist(recipeIngredient93);
            em.persist(recipeIngredient94);
            em.persist(recipeIngredient95);

            Step step91=createStep(recipe9,1L,"큰 그릇에 밀가루를 넣어주세요.");
            Step step92=createStep(recipe9,2L,"베이킹 파우더를 넣고 달걀을 깨서 넣습니다.");
            Step step93=createStep(recipe9,3L,"그릇에 꿀과 사과즙을 부어주세요.");
            Step step94=createStep(recipe9,4L,"치즈를 넣고 골고루 섞어줍니다.");
            Step step95=createStep(recipe9,5L,"혼합물이 반죽이 되도록 손으로 치대줍니다.");
            Step step96=createStep(recipe9,6L,"반죽을 유산지 위에 올리고 밀대를 사용해서 반죽을 1cm 두께로 밀어주세요.");
            Step step97=createStep(recipe9,6L,"쿠키틀로 반죽을 잘라주세요.");
            Step step98=createStep(recipe9,6L,"베이킹 시트 위에 올리고 화씨 350도에서 20분 정도 구워줍니다.");
            Step step99=createStep(recipe9,6L,"완전히 식힌 후 급여해주세요.");

            em.persist(step91);
            em.persist(step92);
            em.persist(step93);
            em.persist(step94);
            em.persist(step95);
            em.persist(step96);
            em.persist(step97);
            em.persist(step98);
            em.persist(step99);

            Recipe recipe10=createRecipe("오트밀 코코넛 쿠키",member1,"맛있고 건강에 좋은 오트밀 코코넛 쿠키! 사랑 가득 담아서 만들어봐요!",
                    url,LocalDateTime.now());
            em.persist(recipe10);
            RecipeIngredient recipeIngredient101=createRecipeIngredient(ingredient8,recipe10,"1개");
            RecipeIngredient recipeIngredient102=createRecipeIngredient(ingredient35,recipe10,"1/4 cup");
            RecipeIngredient recipeIngredient103=createRecipeIngredient(ingredient28,recipe10,"1 cup");
            RecipeIngredient recipeIngredient104=createRecipeIngredient(ingredient36,recipe10,"1/4 cup");
            RecipeIngredient recipeIngredient105=createRecipeIngredient(ingredient32,recipe10,"2 tbsp");
            RecipeIngredient recipeIngredient106=createRecipeIngredient(ingredient30,recipe10,"오트밀 가루");

            em.persist(recipeIngredient101);
            em.persist(recipeIngredient102);
            em.persist(recipeIngredient103);
            em.persist(recipeIngredient104);
            em.persist(recipeIngredient105);
            em.persist(recipeIngredient106);

            Step step101=createStep(recipe10,1L,"큰 그릇에 통밀가루를 넣어주세요.");
            Step step102=createStep(recipe10,2L,"오트밀 가루와 코코넛 가루를 넣고 골고루 섞어줍니다.");
            Step step103=createStep(recipe10,3L,"식물성 오일과 꿀, 달걀을 넣고 골고루 섞어 반죽을 만들어 주세요.");
            Step step104=createStep(recipe10,4L,"평평한 표면 위에 유산지를 깔고 반죽을 올려주세요.");
            Step step105=createStep(recipe10,5L,"밀대를 사용해서 반죽을 1cm 두께로 밀어주세요.");
            Step step106=createStep(recipe10,6L,"쿠키틀을 이용해 반죽을 잘라주세요.");
            Step step107=createStep(recipe10,6L,"베이킹 시트 위에 올려주세요.");
            Step step108=createStep(recipe10,6L,"화씨 370도에서 12-15분 정도 구워주세요.");
            Step step109=createStep(recipe10,6L,"음식을 완전히 식힌 후 급여해주세요.");

            em.persist(step101);
            em.persist(step102);
            em.persist(step103);
            em.persist(step104);
            em.persist(step105);
            em.persist(step106);
            em.persist(step107);
            em.persist(step108);
            em.persist(step109);




//            Recipe recipe2=createRecipe("멍김밥2",member2,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe3=createRecipe("멍김밥3",member2,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe4=createRecipe("멍김밥4",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe5=createRecipe("멍김밥5",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe6=createRecipe("멍김밥6",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe7=createRecipe("멍김밥7",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe8=createRecipe("멍김밥8",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe9=createRecipe("멍김밥9",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe10=createRecipe("멍김밥10",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe11=createRecipe("멍김밥11",member2,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe12=createRecipe("멍김밥12",member2,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe13=createRecipe("멍김밥13",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe14=createRecipe("멍김밥14",member3,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());
//            Recipe recipe15=createRecipe("멍김밥15",member1,"맛있는 멍김밥을 만들어보아요. 우리 지산이도 좋아하는 음식을 여러분과 함께 만들고 싶어 레시피 공유합니다. 모두들 맛있게 만들고, 즐겨봅시다.",
//                    url,LocalDateTime.now());

//            em.persist(recipe1);
//            em.persist(recipe2);
//            em.persist(recipe3);
//            em.persist(recipe4);
//            em.persist(recipe5);
//            em.persist(recipe6);
//            em.persist(recipe7);
//            em.persist(recipe8);
//            em.persist(recipe9);
//            em.persist(recipe10);
//            em.persist(recipe11);
//            em.persist(recipe12);
//            em.persist(recipe13);
//            em.persist(recipe14);
//            em.persist(recipe15);


//            RecipeIngredient recipeIngredient1=createRecipeIngredient(ingredient1,recipe1,"1개");
//            RecipeIngredient recipeIngredient2=createRecipeIngredient(ingredient2,recipe1,"0.5개");
//            RecipeIngredient recipeIngredient3=createRecipeIngredient(ingredient3,recipe1,"100g");
//            RecipeIngredient recipeIngredient4=createRecipeIngredient(ingredient4,recipe1,"2개");
//            RecipeIngredient recipeIngredient5=createRecipeIngredient(ingredient5,recipe1,"150g");
//            RecipeIngredient recipeIngredient6=createRecipeIngredient(ingredient5,recipe2,"150g");
//            RecipeIngredient recipeIngredient7=createRecipeIngredient(ingredient6,recipe2,"2개");
//            RecipeIngredient recipeIngredient8=createRecipeIngredient(ingredient7,recipe3,"3개");
//            RecipeIngredient recipeIngredient9=createRecipeIngredient(ingredient8,recipe4,"0.5개");
//            RecipeIngredient recipeIngredient10=createRecipeIngredient(ingredient1,recipe5,"1개");
//            RecipeIngredient recipeIngredient11=createRecipeIngredient(ingredient3,recipe6,"120g");
//            RecipeIngredient recipeIngredient12=createRecipeIngredient(ingredient5,recipe2,"150g");
//            RecipeIngredient recipeIngredient13=createRecipeIngredient(ingredient6,recipe7,"2개");
//            RecipeIngredient recipeIngredient14=createRecipeIngredient(ingredient7,recipe8,"3개");
//            RecipeIngredient recipeIngredient15=createRecipeIngredient(ingredient8,recipe9,"0.5개");
//            RecipeIngredient recipeIngredient16=createRecipeIngredient(ingredient1,recipe9,"1개");
//            RecipeIngredient recipeIngredient17=createRecipeIngredient(ingredient3,recipe6,"120g");
//            RecipeIngredient recipeIngredient18=createRecipeIngredient(ingredient5,recipe2,"150g");
//            RecipeIngredient recipeIngredient19=createRecipeIngredient(ingredient6,recipe2,"2개");
//            RecipeIngredient recipeIngredient20=createRecipeIngredient(ingredient7,recipe3,"3게");
//            RecipeIngredient recipeIngredient21=createRecipeIngredient(ingredient8,recipe4,"0.5개");
//            RecipeIngredient recipeIngredient22=createRecipeIngredient(ingredient1,recipe5,"1개");
//            RecipeIngredient recipeIngredient23=createRecipeIngredient(ingredient3,recipe6,"120g");
//            RecipeIngredient recipeIngredient24=createRecipeIngredient(ingredient3,recipe10,"120g");
//            RecipeIngredient recipeIngredient25=createRecipeIngredient(ingredient5,recipe10,"150g");
//            RecipeIngredient recipeIngredient26=createRecipeIngredient(ingredient6,recipe11,"2개");
//            RecipeIngredient recipeIngredient27=createRecipeIngredient(ingredient7,recipe12,"3개");
//            RecipeIngredient recipeIngredient28=createRecipeIngredient(ingredient8,recipe12,"0.5개");
//            RecipeIngredient recipeIngredient29=createRecipeIngredient(ingredient1,recipe13,"1개");
//            RecipeIngredient recipeIngredient30=createRecipeIngredient(ingredient1,recipe14,"1개");
//            RecipeIngredient recipeIngredient31=createRecipeIngredient(ingredient3,recipe14,"120g");
//            RecipeIngredient recipeIngredient32=createRecipeIngredient(ingredient3,recipe15,"120g");

//            em.persist(recipeIngredient1);
//            em.persist(recipeIngredient2);
//            em.persist(recipeIngredient3);
//            em.persist(recipeIngredient4);
//            em.persist(recipeIngredient5);
//            em.persist(recipeIngredient6);
//            em.persist(recipeIngredient7);
//            em.persist(recipeIngredient8);
//            em.persist(recipeIngredient9);
//            em.persist(recipeIngredient10);
//            em.persist(recipeIngredient11);
//            em.persist(recipeIngredient12);
//            em.persist(recipeIngredient13);
//            em.persist(recipeIngredient14);
//            em.persist(recipeIngredient15);
//            em.persist(recipeIngredient16);
//            em.persist(recipeIngredient17);
//            em.persist(recipeIngredient18);
//            em.persist(recipeIngredient19);
//            em.persist(recipeIngredient20);
//            em.persist(recipeIngredient21);
//            em.persist(recipeIngredient22);
//            em.persist(recipeIngredient23);
//            em.persist(recipeIngredient24);
//            em.persist(recipeIngredient25);
//            em.persist(recipeIngredient26);
//            em.persist(recipeIngredient27);
//            em.persist(recipeIngredient28);
//            em.persist(recipeIngredient29);
//            em.persist(recipeIngredient30);
//            em.persist(recipeIngredient31);
//            em.persist(recipeIngredient32);


//            Step step1=createStep(recipe1,1L,"재료를 잘라주세요");
//            Step step2=createStep(recipe1,2L,"모든 재료를 볶아주세요");
//            Step step3=createStep(recipe1,3L,"플레이팅");
//            Step step4=createStep(recipe2,1L,"재료를 잘라주세요");
//            Step step5=createStep(recipe3,1L,"재료를 잘라주세요");
//            Step step6=createStep(recipe4,1L,"재료를 잘라주세요");
//            Step step7=createStep(recipe5,1L,"재료를 잘라주세요");
//            Step step8=createStep(recipe6,1L,"재료를 잘라주세요");
//            Step step9=createStep(recipe7,1L,"재료를 잘라주세요");
//            Step step10=createStep(recipe8,1L,"재료를 잘라주세요");
//            Step step11=createStep(recipe9,1L,"재료를 잘라주세요");
//            Step step12=createStep(recipe10,1L,"재료를 잘라주세요");
//            Step step13=createStep(recipe11,1L,"재료를 잘라주세요");
//            Step step14=createStep(recipe12,1L,"재료를 잘라주세요");
//            Step step15=createStep(recipe13,1L,"재료를 잘라주세요");
//            Step step16=createStep(recipe14,1L,"재료를 잘라주세요");
//            Step step17=createStep(recipe15,1L,"재료를 잘라주세요");

//            em.persist(step1);
//            em.persist(step2);
//            em.persist(step3);
//            em.persist(step4);
//            em.persist(step5);
//            em.persist(step6);
//            em.persist(step7);
//            em.persist(step8);
//            em.persist(step9);
//            em.persist(step10);
//            em.persist(step11);
//            em.persist(step12);
//            em.persist(step13);
//            em.persist(step14);
//            em.persist(step15);
//            em.persist(step16);
//            em.persist(step17);


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
//            member.setRole(Role.USER);
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