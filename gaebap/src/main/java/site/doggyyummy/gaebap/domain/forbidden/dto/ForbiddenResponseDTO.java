package site.doggyyummy.gaebap.domain.forbidden.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForbiddenResponseDTO {      //필요 없는데 걍 만듬 ,연습용 + 추후 변경 변경 가능성
    private Long pet_id;
    private Long ingredient_id;

    public static ForbiddenResponseDTO toDTO(Forbidden forbidden){
        ForbiddenResponseDTO forbiddenResponseDTO = new ForbiddenResponseDTO();
        forbiddenResponseDTO.setIngredient_id(forbidden.getIngredient().getId());
        forbiddenResponseDTO.setPet_id(forbidden.getPet().getId());
        return forbiddenResponseDTO;
    }

}
