package site.doggyyummy.gaebap.domain.forbidden.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForbiddenResponseDTO {      //순환 참조 방지용으로 DTO 필수임
    private Long petId;
    private Long ingredientId;

    private String ingredientName;
    public static ForbiddenResponseDTO toDTO(Forbidden forbidden){
        ForbiddenResponseDTO forbiddenResponseDTO = new ForbiddenResponseDTO();
        forbiddenResponseDTO.setIngredientId(forbidden.getIngredient().getId());
        forbiddenResponseDTO.setPetId(forbidden.getPet().getId());
        return forbiddenResponseDTO;
    }

}
