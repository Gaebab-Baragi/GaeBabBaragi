package site.doggyyummy.gaebap.domain.comment.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.comment.dto.CommentRequestDTO;
import site.doggyyummy.gaebap.domain.comment.dto.CommentResponseDTO;
import site.doggyyummy.gaebap.domain.comment.service.CommentService;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.global.security.util.SecurityUtil;


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
        Member loginMember= SecurityUtil.getCurrentLoginMember();
        commentService.create(dto,loginMember);
    }
    @PutMapping("")
    public void modify(@RequestBody CommentRequestDTO dto){
        commentService.modify(dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id){
        Member loginMember=SecurityUtil.getCurrentLoginMember();
        commentService.delete(id,loginMember);
    }
}
