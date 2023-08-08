package site.doggyyummy.gaebap.domain.recipe.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "Recipe Controller", description = "레시피 API")

public class RecipeController {

    private final RecipeService recipeService;

    //레시피 등록
    @Operation(summary = "create recipe", description = "레시피 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "upload success", content=@Content(schema = @Schema(implementation = RecipeUploadResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "upload fail")
    })
    @PostMapping("/recipes/new")
    public RecipeUploadResponseDto uploadRecipes(@RequestPart RecipeUploadRequestDto recipeUploadRequestDto, @RequestPart MultipartFile recipeImage,@RequestPart MultipartFile recipeVideo,@RequestPart MultipartFile[] stepImages) throws IOException {

        Member member = SecurityUtil.getCurrentLoginMember();
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

//    @Operation(summary = "create recipe", description = "레시피 등록")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200",description = "upload success", content=@Content(schema = @Schema(implementation = RecipeUploadResponseDto.class))),
//            @ApiResponse(responseCode = "400", description = "upload fail")
//    })
//    @PostMapping("/recipes/new")
//    public RecipeUploadResponseDto uploadRecipes(@RequestPart RecipeUploadRequestDto recipeUploadRequestDto, @RequestPart MultipartFile recipeImage,@RequestPart MultipartFile recipeVideo) throws IOException {
//
//        Member member = SecurityUtil.getCurrentLoginMember();
//        if(member==null){
//            throw new UnauthorizedException(HttpStatus.SC_UNAUTHORIZED,"로그인을 해주세요");
//        }
//        try {
//            RecipeUploadResponseDto resDto=recipeService.uploadRecipe(member,recipeUploadRequestDto,recipeImage,recipeVideo);
//            return resDto;
//        }catch (IllegalArgumentException e){
//            return new RecipeUploadResponseDto(null,null,HttpStatus.SC_BAD_REQUEST,e.getMessage());
//        }
//    }
    //레시피 id로 조회
    @Operation(summary = "search recipe by recipeId",description = "레시피 상세 조회")
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
    @Operation(summary = "search recipes by writer",description = "특정 작성자가 작성한 레시피 조회")
    @GetMapping("/recipes/writer")
    public RecipeFindByMemberIdResponseDto findRecipeByMemberId(){
        Long memberId = SecurityUtil.getCurrentLoginMember().getId();
        log.info("memberId = {}", memberId);
        return recipeService.findRecipeByMemberId(memberId);
    }

    //레시피 전체 조회 (레시피 제목, 작성자)
    @Operation(summary = "search all recipes", description = "레시피 전체 목록 조회")
    @GetMapping("/recipes")
    public RecipeAllResponseDto allRecipes(){
        return recipeService.allRecipe();
    }

    //레시피 삭제
    @Operation(summary = "delete recipe", description = "레시피 삭제")
    @DeleteMapping("/recipes/{id}")
    public RecipeDeleteResponseDto deleteRecipe(@PathVariable ("id") Long id){
        Member member = SecurityUtil.getCurrentLoginMember();
        try{
            return recipeService.deleteRecipe(id,member);
        }catch (UnauthorizedException e){
            return new RecipeDeleteResponseDto(e.getStatusCode(),e.getMessage(),null);
        }catch (NotFoundRecipeException e){
            return new RecipeDeleteResponseDto(e.getStatusCode(),e.getMessage(),null);
        }
    }

    //레시피 수정
    @Operation(summary = "modify recipe", description = "레시피 수정")
    @PutMapping("/recipes/{id}")
    public RecipeModifyResponseDto modifyRecipe(@PathVariable("id") Long id,@RequestPart RecipeModifyRequestDto reqDto,@RequestPart MultipartFile newRecipeImage,@RequestPart MultipartFile newRecipeVideo,@RequestPart MultipartFile[] newStepImages) throws IOException{
        Member member = SecurityUtil.getCurrentLoginMember();
        try {
            System.out.println("^^^^^^^^"+newStepImages.length);
            recipeService.modifyRecipe(member,id, reqDto,newRecipeImage,newRecipeVideo,newStepImages);
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
    @Operation(summary = "search recipe by title,ingredients,petForbiddenIngredients",description = "레시피 제목, 포함 재료, 나의 반려견이 먹을 수 있는 재료를 포함하는 레시피 검색")
    @PostMapping("/recipes/searchlike")
    public RecipeSearchLikeResponseDto searchRecipeLikeTitle(@RequestBody RecipeSearchLikeRequestDto reqDto){
        return recipeService.searchRecipeLike(reqDto);
    }

    @Operation(summary = "search all ingredients",description = "모든 재료 조회")
    @GetMapping("/ingredients")
    public IngredientAllResponseDto searchAllIngredients(){
        return recipeService.searchAllIngredients();
    }
}