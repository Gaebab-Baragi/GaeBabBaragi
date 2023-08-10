package site.doggyyummy.gaebap.domain.comment.service;


import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.comment.repository.CommentRepository;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.exception.UnauthorizedException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void create(CommentRequestDTO dto, Member loginMember) {
        dto.setMemberId(loginMember.getId());
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
    public void delete(Long id,Member loginMember) {
        Comment comment=commentRepository.selectComment(id);
        if(comment.getWriter().getId()!= loginMember.getId()){
            throw new UnauthorizedException(HttpStatus.SC_UNAUTHORIZED, "권한이 없습니다.");
        }
        commentRepository.delete(id);
    }
}
