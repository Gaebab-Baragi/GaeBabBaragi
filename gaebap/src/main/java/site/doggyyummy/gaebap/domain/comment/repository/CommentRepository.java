package site.doggyyummy.gaebap.domain.comment.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void create(Comment comment){
        em.persist(comment);
    }
    public Comment selectOne (long id){
        Comment comment = em.createQuery("select c from Comment c " +
                        "join fetch c.recipe r " +
                        "join fetch c.writer w " +
                        "where c.id = :id ",Comment.class)
                .setParameter("id" ,id)
                .getSingleResult();
        return comment;
    }
    public List<Comment> selectByRecipe (long recipeId){
        List<Comment> comments = em.createQuery("select c from Comment c " +
                        "join fetch c.recipe r " +
                        "join fetch c.writer w " +
                        "where c.recipe.id = :recipeId ")
                .setParameter("recipeId" ,recipeId)
                .getResultList();
        return comments;
    }

    public void delete(Long id){
        Comment comment = em.find(Comment.class,id);
        if(comment!=null){
            em.remove(comment);
        }
    }

}
