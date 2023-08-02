package site.doggyyummy.gaebap.domain.comment.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import site.doggyyummy.gaebap.domain.comment.service.CommentService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("")
    public List<CommentResponseDTO> selectByRecipe(@RequestParam(name= "recipe_id") Long recipeId){
        List<CommentResponseDTO> comments = commentService.selectByRecipe(recipeId);
        return comments;
    }

    @PostMapping("")
    public void create(@RequestBody CommentRequestDTO dto) {
        commentService.create(dto);
    }
    @PutMapping("")
    public void modify(@RequestBody CommentRequestDTO dto){
        commentService.modify(dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id){
        commentService.delete(id);
    }
}
