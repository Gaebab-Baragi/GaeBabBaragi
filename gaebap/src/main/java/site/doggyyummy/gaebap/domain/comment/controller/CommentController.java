package site.doggyyummy.gaebap.domain.comment.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.comment.service.CommentService;
import site.doggyyummy.gaebap.domain.forbidden.service.ForbiddenServiceImpl;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forbidden")
public class CommentController {

    CommentService commentService;

    @GetMapping("")
    public List<CommentResponseDTO> selectByRecipe(@RequestParam(name= "recipe_id") Long recipeId){
        List<CommentResponseDTO> comments = commentService.selectByRecipe(recipeId);
        return comments;
    }


    @PostMapping("")
        public void create(CommentRequestDTO dto) {
            commentService.create(dto);
    }
    @PutMapping("")
    public void modify(CommentRequestDTO dto){
        commentService.modify(dto);
    }
    @DeleteMapping("")
    public void delete(Long id){         //API 명세상으로는 Id를 params로 받지만 회의 때 petId,ingredientId를 받자 했던 거 같아서 일단 그렇게함
        commentService.delete(id);
    }
}
