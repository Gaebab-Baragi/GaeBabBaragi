package site.doggyyummy.gaebap.domain.bookmark.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkRequestDTO;
import site.doggyyummy.gaebap.domain.bookmark.dto.BookmarkResponseDTO;
import site.doggyyummy.gaebap.domain.bookmark.service.BookmarkServiceImpl;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.global.security.util.SecurityUtil;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
@Schema(description = "좋아요 기능을 위한 컨트롤러")
public class BookmarkController {

    private final BookmarkServiceImpl bookmarkService;

    @Operation(description = "현재 로그인한 회원의 좋아요 목록을 불러옴")
    @GetMapping("/my")
    public ResponseEntity<List<BookmarkResponseDTO>> selectMyBookmarks(){
       return new ResponseEntity<>(bookmarkService.selectByMember(SecurityUtil.getCurrentLoginMember()), HttpStatus.OK);
    }

    @Operation(description = "해당 레시피의 좋아요 수를 불러옴")
    @GetMapping("/{recipe_id}")
    public ResponseEntity<Long> selectByRecipe(@PathVariable(name= "recipe_id") Long bookmarkId){
        long count = bookmarkService.selectByRecipe(bookmarkId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Operation(description = "좋아요 등록")
    @PostMapping("/{recipe_id}")
    public void create(@PathVariable Long recipe_id) {
        Member loginMember=SecurityUtil.getCurrentLoginMember();
        bookmarkService.create(loginMember,recipe_id);
    }

    @Operation(description = "좋아요 해제")
    @DeleteMapping("")     //id를 프론트단에서 알기 힘드므로 pet_id,ingredient_id를 받음
    public void delete(@RequestBody BookmarkRequestDTO bookmarkRequestDTO){         //API 명세상으로는 Id를 params로 받지만 회의 때 petId,ingredientId를 받자 했던 거 같아서 일단 그렇게함
        bookmarkService.delete(bookmarkRequestDTO);
    }
}
