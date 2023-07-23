package site.doggyyummy.gaebap.domain.forbidden.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForbiddenResponseDTO {      //필요 없는데 걍 만듬 ,연습용 + 추후 변경 변경 가능성
    private Long petId;
    private Long ingredientId;

    public static ForbiddenResponseDTO toDTO(Forbidden forbidden){
        ForbiddenResponseDTO forbiddenResponseDTO = new ForbiddenResponseDTO();
        forbiddenResponseDTO.setIngredientId(forbidden.getIngredient().getId());
        forbiddenResponseDTO.setPetId(forbidden.getPet().getId());
        return forbiddenResponseDTO;
    }

}
