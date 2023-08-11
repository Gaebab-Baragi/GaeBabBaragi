package site.doggyyummy.gaebap.domain.comment.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.comment.repository.CommentRepository;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.Step;
import site.doggyyummy.gaebap.domain.recipe.exception.UnauthorizedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AmazonS3 awsS3Client;
    @Override
    @Transactional
    public void create(CommentRequestDTO dto, Member loginMember, MultipartFile commentImg) throws IOException {
        dto.setMemberId(loginMember.getId());
        Comment comment = dto.toEntity();
        commentRepository.create(comment);
        Map<String,String> commentMap=uploadFile(comment,commentImg);
        comment.setCommentImgKey(commentMap.get("s3Key"));
        comment.setCommentImgUrl(commentMap.get("s3Url"));
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

    public Map<String, String> uploadFile(Object object, MultipartFile multipartFile) throws IOException {
        String s3Key = "";
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        if (object instanceof Comment) {
            s3Key = "comment/" + ((Comment) object).getId() + "/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        }

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(multipartFile.getInputStream().available());

        awsS3Client.putObject("sh-bucket", s3Key, multipartFile.getInputStream(), objMeta);
        String s3Url = awsS3Client.getUrl("sh-bucket", s3Key).toString();
        Map<String, String> map = new HashMap<>();
        map.put("s3Key", s3Key);
        map.put("s3Url", s3Url);
        return map;
    }
}
