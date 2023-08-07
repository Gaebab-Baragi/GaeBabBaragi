package site.doggyyummy.gaebap.domain.bookmark.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {
    private final EntityManager em;

    public void create(Bookmark bookmark){
        em.persist(bookmark);
    }
    public Bookmark selectOne (long memberId, long recipeId){
        Bookmark bookmark = (Bookmark) em.createQuery("select b from Bookmark b" +
                        " where b.member.id = :memberId" +
                        " and b.recipe.id = :recipeId"
                )
                .setParameter("memberId" ,memberId)
                .setParameter("recipeId" ,recipeId)
                .getSingleResult();
        return bookmark;
    }

    public List<Bookmark> selectByRecipe (long recipeId){
        List<Bookmark> bookmarks = em.createQuery("select b from Bookmark b " +
                        "left join fetch b.recipe r " +
                        "left join fetch b.member m " +
                        "where b.recipe.id = :recipeId")
                .setParameter("recipeId" ,recipeId)
                .getResultList();
        return bookmarks;
    }

    public void delete(Bookmark bookmark){
        em.createQuery("delete   Bookmark b" +
                " where b.recipe.id = :recipeId" +
                " and b.member.id = :memberId")
                .setParameter("recipeId" ,bookmark.getRecipe().getId())
                .setParameter("memberId" ,bookmark.getMember().getId())
                .executeUpdate();
    }

    public List<Bookmark> selectByMember(Member member){
        List<Bookmark> bookmark = em.createQuery("select b from Bookmark b" +
                        " where b.member.id = :memberId"
                )
                .setParameter("memberId" ,member.getId())
                .getResultList();
        return bookmark;
    }
}
