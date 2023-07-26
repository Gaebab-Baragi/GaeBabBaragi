package site.doggyyummy.gaebap.domain.bookmark.entity.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.bookmark.entity.dto.BookmarkRequestDTO;
import site.doggyyummy.gaebap.domain.bookmark.entity.dto.BookmarkResponseDTO;
import site.doggyyummy.gaebap.domain.bookmark.entity.service.BookmarkServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkServiceImpl bookmarkService;

    @GetMapping("/{recipe_id}")
    public List<BookmarkResponseDTO> selectByPet(@PathVariable(name= "recipe_id") Long bookmarkId){
        List<BookmarkResponseDTO> bookmarks = bookmarkService.selectByRecipe(bookmarkId);
        return bookmarks;
    }

    @PostMapping("")
    public void create(@RequestBody BookmarkRequestDTO bookmarkRequestDTO) {
        bookmarkService.create(bookmarkRequestDTO);
    }

    @DeleteMapping("")     //id를 프론트단에서 알기 힘드므로 pet_id,ingredient_id를 받음
    public void delete(@RequestBody BookmarkRequestDTO bookmarkRequestDTO){         //API 명세상으로는 Id를 params로 받지만 회의 때 petId,ingredientId를 받자 했던 거 같아서 일단 그렇게함
        bookmarkService.delete(bookmarkRequestDTO);
    }

}
