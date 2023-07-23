package site.doggyyummy.gaebap.domain.comment.service;

import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;

public interface CommentService {
    void create(CommentRequestDTO dto);
    List<CommentResponseDTO> selectByRecipe (Long recipeId);

    void modify(CommentRequestDTO dto);

    void delete(Long id);

}
