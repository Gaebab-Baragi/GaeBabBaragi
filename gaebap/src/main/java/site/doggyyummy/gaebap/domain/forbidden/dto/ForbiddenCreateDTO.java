package site.doggyyummy.gaebap.domain.forbidden.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class ForbiddenCreateDTO {
    private Long pet_id;
    private Long ingredient_id;

    public static ForbiddenCreateDTO toEntity(pet_id, ingredient_id){

        return
    }

}
