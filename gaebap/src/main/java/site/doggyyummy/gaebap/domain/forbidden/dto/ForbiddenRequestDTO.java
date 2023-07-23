package site.doggyyummy.gaebap.domain.forbidden.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;

@AllArgsConstructor
@Data
public class ForbiddenRequestDTO {    //필요 없는데 걍 만듬 ,연습용 + 추후 변경 변경 가능성
    private Long petId;
    private Long ingredientId;

    public Forbidden toEntity(){
        Forbidden forbidden = new Forbidden();
        Pet pet = new Pet();
        pet.setId(this.petId);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(this.ingredientId);

        return forbidden;
    }

}
