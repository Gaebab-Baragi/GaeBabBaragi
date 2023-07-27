package site.doggyyummy.gaebap.domain.bookmark.service;

import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkRequestDTO;

public interface BookmarkService {
    void create(BookmarkRequestDTO bookmarkRequestDTO);
    long selectByRecipe (Long recipeId);

    void delete(BookmarkRequestDTO bookmarkRequestDTO);

}
