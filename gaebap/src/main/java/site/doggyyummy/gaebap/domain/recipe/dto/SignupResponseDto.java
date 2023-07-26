package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String name;
    private Long id;

    public SignupResponseDto(Long id, String name){
        this.id=id;
        this.name=name;
    }
}
