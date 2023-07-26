package site.doggyyummy.gaebap.domain.comment.service;

import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import java.util.List;

public interface CommentService {
    void create(CommentRequestDTO dto);
    List<CommentResponseDTO> selectByRecipe (Long recipeId);

    void modify(CommentRequestDTO dto);

    void delete(Long id);

}
