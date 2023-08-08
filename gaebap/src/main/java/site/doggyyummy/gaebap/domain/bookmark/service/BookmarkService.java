package site.doggyyummy.gaebap.domain.bookmark.service;

import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkRequestDTO;
import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkResponseDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.List;

public interface BookmarkService {
    void create(Member loginMember,Long id);
    long selectByRecipe (Long recipeId);
    void delete(BookmarkRequestDTO bookmarkRequestDTO);

    List<BookmarkResponseDTO> selectByMember(Member member);

}
