package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

@Getter
@Setter
public class RecipeDeleteResponseDto {
    private String message;
    private RecipeDto recipe;
    public RecipeDeleteResponseDto(Recipe recipe){
        this.recipe=new RecipeDto(recipe.getTitle(),recipe.getId());
        this.message=this.recipe.getTitle()+"을 삭제 했습니다.";
    }
    @Getter
    public static class RecipeDto{
        private String title;
        private Long id;
        public RecipeDto(String title,Long id){
            this.title=title;
            this.id=id;
        }
    }

}
