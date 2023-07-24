package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;

//레시피의 제목, 작성자 이름만
@Getter
@Setter
public class RecipeAllResponseDto {
    private String title;

    @Getter
    public static class MemberDto{
        private String name;
        public MemberDto(String name){
            this.name=name;
        }
    }
}
