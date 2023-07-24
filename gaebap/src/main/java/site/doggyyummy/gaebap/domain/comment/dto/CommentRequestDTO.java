package site.doggyyummy.gaebap.domain.comment.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRequestDTO {
    Long id;
    @JsonProperty("recipe_id")
    Long recipeId;
    @JsonProperty("member_id")
    Long memberId;
    String content;
    LocalDateTime writeTime;

    public Comment toEntity(){
        Comment comment = new Comment();

        if(this.id!=null){
            comment.setId(this.id);
        }

        Member member= new Member();
        member.setId(this.recipeId);
        Recipe recipe = new Recipe();
        recipe.setId(this.recipeId);

        comment.setRecipe(recipe);
        comment.setWriter(member);
        comment.setWriteTime(this.writeTime);
        comment.setContent(content);

        return comment;
    }

}
