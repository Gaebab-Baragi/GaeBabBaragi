package site.doggyyummy.gaebap.domain.bookmark.entity.service;

import site.doggyyummy.gaebap.domain.bookmark.entity.dto.BookmarkRequestDTO;
import site.doggyyummy.gaebap.domain.bookmark.entity.dto.BookmarkResponseDTO;

import java.util.List;

public interface BookmarkService {
    void create(BookmarkRequestDTO bookmarkRequestDTO);
    List<BookmarkResponseDTO>  selectByRecipe (Long recipeId);

    void delete(BookmarkRequestDTO bookmarkRequestDTO);

}
