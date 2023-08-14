package site.doggyyummy.gaebap.domain.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDTO {      //필요 없는데 걍 만듬 ,연습용 + 추후 변경 변경 가능성
    private Long id;
    private Long writerId;
    private String writer;
    private Long recipeId;
    private String commentImgUrl;

    private LocalDateTime writeTime;

    private String content;
    private String profileUrl;

    public static CommentResponseDTO toDTO(Comment comment){
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setProfileUrl(comment.getWriter().getProfileUrl());
        dto.setWriter(comment.getWriter().getNickname());
        dto.setRecipeId(comment.getRecipe().getId());
        dto.setWriterId(comment.getWriter().getId());
        dto.setWriteTime(comment.getWriteTime());
        dto.setCommentImgUrl(comment.getCommentImgUrl());
        return dto;
    }



}
