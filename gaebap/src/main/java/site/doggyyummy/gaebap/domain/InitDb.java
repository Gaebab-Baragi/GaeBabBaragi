package site.doggyyummy.gaebap.domain;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.awt.print.Book;

/**
 * 종 주문 2개
 * * userA
 * 	 * JPA1 BOOK
 * 	 * JPA2 BOOK
 * * userB
 * 	 * SPRING1 BOOK
 * 	 * SPRING2 BOOK
 */
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
            System.out.println("Init1" + this.getClass());
            Member member = createMember("배찬일");
            em.persist(member);
            Recipe recipe = createRecipe("제목1",member);
            em.persist(recipe);
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


    }
}

