package site.doggyyummy.gaebap.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.recipe.dto.*;
import site.doggyyummy.gaebap.domain.recipe.service.RecipeService;

@RestController
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    //레시피 등록
    @PostMapping("/recipes/new")
    public RecipeUploadResponseDto uploadRecipes(@RequestBody RecipeUploadRequestDto recipeUploadRequestDto){
        return recipeService.uploadRecipe(recipeUploadRequestDto);
    }

    //레시피 id로 조회
    @GetMapping("/recipes/{id}")
    public RecipeFindByIdResponseDto findByRecipeId(@PathVariable Long id){
        return recipeService.findRecipeByRecipeId(id);
    }

    //멤버 id가 등록한 레시피 조회
    @GetMapping("/recipes/writer/{member_id}")
    public RecipeFindByMemberIdResponseDto findRecipeByMemberId(@PathVariable ("member_id") Long id){
        return recipeService.findRecipeByMemberId(id);
    }

    //임시 회원 등록
    @PostMapping("/members/new")
    public SignupResponseDto signUp(@RequestBody SignupRequestDto reqDto){
        return recipeService.signUp(reqDto);
    }

    //레시피 전체 조회 (레시피 제목, 작성자)
    @GetMapping("/recipes")
    public RecipeAllResponseDto allRecipes(){
        return recipeService.allRecipe();
    }

    //레시피 삭제
    @DeleteMapping("/recipes/{id}")
    public RecipeDeleteResponseDto deleteRecipe(@PathVariable ("id") Long id){
        return recipeService.deleteRecipe(id);
    }

    //레시피 수정
    @PutMapping("/recipes/{id}")
    public String modifyRecipe(@PathVariable("id") Long id,@RequestBody RecipeModifyRequestDto reqDto){
        recipeService.modifyRecipe(id,reqDto);
        return "ok";
    }

    //테스트용 (버킷에 있는 객체 전부 삭제)
    @GetMapping("/s3DeleteAll")
    public String s3DeleteAll(){
        recipeService.s3DeleteAll();
        return "ok";
    }
}