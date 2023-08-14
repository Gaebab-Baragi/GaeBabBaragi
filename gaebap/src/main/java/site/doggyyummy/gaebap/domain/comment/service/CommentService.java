package site.doggyyummy.gaebap.domain.comment.service;

import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.io.IOException;
import java.util.List;

public interface CommentService {
    void create(CommentRequestDTO dto, Member loginMember, MultipartFile commentImg) throws IOException;
    List<CommentResponseDTO> selectByRecipe (Long recipeId);

    void modify(CommentRequestDTO dto);

    void delete(Long id,Member loginMember);

}
