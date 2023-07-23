package site.doggyyummy.gaebap.domain.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDTO {      //필요 없는데 걍 만듬 ,연습용 + 추후 변경 변경 가능성
    private Long id;
    private Long writerId;

    private Long recipeId;

    private LocalDateTime writeTime;

    private String content;

    public static CommentResponseDTO toDTO(Comment comment){
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setRecipeId(comment.getRecipe().getId());
        dto.setWriterId(comment.getWriter().getId());
        dto.setContent(comment.getContent());
        return dto;
    }



}
