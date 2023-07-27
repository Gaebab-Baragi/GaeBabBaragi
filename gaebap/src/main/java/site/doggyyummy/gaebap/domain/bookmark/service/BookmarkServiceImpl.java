package site.doggyyummy.gaebap.domain.bookmark.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkRequestDTO;
import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkResponseDTO;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;
import site.doggyyummy.gaebap.domain.bookmark.repository.BookmarkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional(readOnly = true)
    @Override
    public long selectByRecipe(Long recipeId) {
        List<Bookmark> bookmarks = null;
        List<BookmarkResponseDTO> bookmarksDTO = null;
        long count = 0L;
        try {
            bookmarks = bookmarkRepository.selectByRecipe(recipeId);
            count = bookmarks.size();
            return count;

        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    @Transactional
    public void create(BookmarkRequestDTO bookmarkRequestDTO) {
        Bookmark bookmark = bookmarkRequestDTO.toEntity();
        bookmarkRepository.create(bookmark);
    }

    @Override
    @Transactional
    public void delete(BookmarkRequestDTO bookmarkRequestDTO) {
        Bookmark bookmark = bookmarkRequestDTO.toEntity();

        bookmarkRepository.delete(bookmark);
    }
}
