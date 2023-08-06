package site.doggyyummy.gaebap.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.dto.*;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.exception.NotFoundRecipeException;
import site.doggyyummy.gaebap.domain.recipe.exception.UnauthorizedException;
import site.doggyyummy.gaebap.domain.recipe.service.RecipeService;
import site.doggyyummy.gaebap.global.security.util.SecurityUtil;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecipeController {

    private final RecipeService recipeService;

    //레시피 등록
    @PostMapping("/recipes/new")
    public RecipeUploadResponseDto uploadRecipes(@RequestPart RecipeUploadRequestDto recipeUploadRequestDto, @RequestPart MultipartFile recipeImage,@RequestPart MultipartFile recipeVideo,@RequestPart MultipartFile[] stepImages) throws IOException {

        Member member=new Member();
        member.setId(1L);
//        Member member = SecurityUtil.getCurrentLoginMember();
        if(member==null){
            throw new UnauthorizedException(HttpStatus.SC_UNAUTHORIZED,"로그인을 해주세요");
        }
        try {
            RecipeUploadResponseDto resDto=recipeService.uploadRecipe(member,recipeUploadRequestDto,recipeImage,recipeVideo,stepImages);
            return resDto;
        }catch (IllegalArgumentException e){
            return new RecipeUploadResponseDto(null,null,HttpStatus.SC_BAD_REQUEST,e.getMessage());
        }
    }
    //레시피 id로 조회
    @GetMapping("/recipes/{id}")
    public RecipeFindByIdResponseDto findByRecipeId(@PathVariable Long id){
        try {
            recipeService.addHit(id);
            return recipeService.findRecipeByRecipeId(id);
        }
        catch (NotFoundRecipeException e){
            return new RecipeFindByIdResponseDto(e.getStatusCode(),e.getMessage());
        }
    }

    //멤버 id가 등록한 레시피 조회
    @GetMapping("/recipes/writer/{member_id}")
    public RecipeFindByMemberIdResponseDto findRecipeByMemberId(@PathVariable ("member_id") Long id){
        return recipeService.findRecipeByMemberId(id);
    }

    //레시피 전체 조회 (레시피 제목, 작성자)
    @GetMapping("/recipes")
    public RecipeAllResponseDto allRecipes(){
        return recipeService.allRecipe();
    }

    //레시피 삭제
    @DeleteMapping("/recipes/{id}")
    public RecipeDeleteResponseDto deleteRecipe(@PathVariable ("id") Long id,@RequestBody RecipeDeleteRequestDto reqDto){
        try{
            return recipeService.deleteRecipe(id,reqDto);
        }catch (UnauthorizedException e){
            return new RecipeDeleteResponseDto(e.getStatusCode(),e.getMessage(),null);
        }catch (NotFoundRecipeException e){
            return new RecipeDeleteResponseDto(e.getStatusCode(),e.getMessage(),null);
        }
    }

    //레시피 수정
    @PutMapping("/recipes/{id}")
    public RecipeModifyResponseDto modifyRecipe(@PathVariable("id") Long id,@RequestPart RecipeModifyRequestDto reqDto,@RequestPart MultipartFile newRecipeImage,@RequestPart MultipartFile newRecipeVideo,@RequestPart MultipartFile[] newStepImages) throws IOException{
        try {
            recipeService.modifyRecipe(id, reqDto,newRecipeImage,newRecipeVideo,newStepImages);
            return new RecipeModifyResponseDto(HttpStatus.SC_OK, "modify Success");
        }catch(UnauthorizedException e){
            return new RecipeModifyResponseDto(e.getStatusCode(),e.getMessage());
        }catch(NotFoundRecipeException e){
            return new RecipeModifyResponseDto(e.getStatusCode(),e.getMessage());
        }
    }

    //테스트용 (버킷에 있는 객체 전부 삭제)
    @GetMapping("/s3DeleteAll")
    public String s3DeleteAll(){
        recipeService.s3DeleteAll();
        return "ok";
    }

    //레시피 검색 like with title
    @PostMapping("/recipes/searchlike")
    public RecipeSearchLikeResponseDto searchRecipeLikeTitle(@RequestBody RecipeSearchLikeRequestDto reqDto){
        return recipeService.searchRecipeLike(reqDto);
    }

}