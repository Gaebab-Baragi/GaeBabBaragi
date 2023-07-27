package site.doggyyummy.gaebap.domain.bookmark.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkRequestDTO;
import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkResponseDTO;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;
import site.doggyyummy.gaebap.domain.bookmark.repository.BookmarkRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookmarkResponseDTO> selectByRecipe(Long recipeId) {
        List<Bookmark> bookmarks = null;
        List<BookmarkResponseDTO> bookmarksDTO = null;
        try {
            bookmarks = bookmarkRepository.selectByRecipe(recipeId);
            bookmarksDTO = bookmarks.stream()
                    .map(BookmarkResponseDTO::toDTO)
                    .collect(Collectors.toList());
            return bookmarksDTO;

        }catch (Exception e){
            e.printStackTrace();
        }
        return bookmarksDTO;
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
