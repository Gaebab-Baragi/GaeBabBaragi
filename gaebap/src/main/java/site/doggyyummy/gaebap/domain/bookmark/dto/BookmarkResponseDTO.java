package site.doggyyummy.gaebap.domain.bookmark.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookmarkResponseDTO {      //순환 참조 방지용으로 DTO 필수임
    private Long memberId;
    private Long recipeId;

    public static BookmarkResponseDTO toDTO(Bookmark bookmark){
        BookmarkResponseDTO bookmarkResponseDTO = new BookmarkResponseDTO();
        bookmarkResponseDTO.setMemberId(bookmark.getMember().getId());
        bookmarkResponseDTO.setRecipeId(bookmark.getRecipe().getId());
        return bookmarkResponseDTO;
    }

}
