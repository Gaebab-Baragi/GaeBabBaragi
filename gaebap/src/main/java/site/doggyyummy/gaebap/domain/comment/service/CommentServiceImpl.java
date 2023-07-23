package site.doggyyummy.gaebap.domain.comment.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.comment.repository.CommentRepository;
import site.doggyyummy.gaebap.domain.forbidden.repository.ForbiddenRepository;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    @Override
    @Transactional
    public void create(CommentRequestDTO dto) {
        Comment comment = dto.toEntity();
        commentRepository.create(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> selectByRecipe(Long recipeId) {
        List<Comment> comments = commentRepository.selectByRecipe(recipeId);
        List<CommentResponseDTO> commentsDTO = comments.stream()
                .map(CommentResponseDTO::toDTO)
                .collect(Collectors.toList());

        return commentsDTO;
    }

    @Override
    @Transactional
    public void modify(CommentRequestDTO dto) {          //dirty checking
        Comment comment = dto.toEntity();
        Comment findComment = commentRepository.selectOne(comment.getId());
        findComment.setContent(comment.getContent());
        findComment.setRecipe(comment.getRecipe());
        findComment.setWriter(comment.getWriter());
        findComment.setWriteTime(comment.getWriteTime());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        commentRepository.delete(id);
    }
}
