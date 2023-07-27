package site.doggyyummy.gaebap.domain.bookmark.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookmarkRequestDTO {    //필요 없는데 걍 만듬 ,연습용 + 추후 변경 변경 가능성
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("recipe_id")
    private Long recipeId;

    public Bookmark toEntity(){
        Bookmark bookmark = new Bookmark();

        Member member = new Member();
        member.setId(memberId);

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        bookmark.setMember(member);
        bookmark.setRecipe(recipe);

        return bookmark;
    }
}
