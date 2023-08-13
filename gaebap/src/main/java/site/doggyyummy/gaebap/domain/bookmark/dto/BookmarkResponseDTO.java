package site.doggyyummy.gaebap.domain.bookmark.dto;


import lombok.*;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BookmarkResponseDTO {      //순환 참조 방지용으로 DTO 필수임
    private Long id;
    private String title;
    private String description;
    private LocalDateTime writtenTime;
    private Long hit;
    private String recipeImageUrl;

    public static BookmarkResponseDTO toDTO(Bookmark bookmark){
        Recipe recipe = bookmark.getRecipe();
        BookmarkResponseDTO bookmarkResponseDTO =
                BookmarkResponseDTO.builder()
                        .id(recipe.getId())
                        .title(recipe.getTitle())
                        .description(recipe.getDescription())
                        .writtenTime(recipe.getWrittenTime())
                        .hit(recipe.getHit())
                        .recipeImageUrl(recipe.getImageUrl())
                        .build();
        return bookmarkResponseDTO;
    }
}
