package site.doggyyummy.gaebap.domain.forbidden.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;

@AllArgsConstructor
@Data
public class ForbiddenRegisterDTO {    //필요 없는데 걍 만듬 ,연습용 + 추후 변경 변경 가능성
    private Long petId;
    private Long ingredientId;

    public static Forbidden toEntity(Pet pet, Ingredient ingredient){
        Forbidden forbidden = new Forbidden();
        forbidden.setPet(pet);
        forbidden.setIngredient(ingredient);

        return forbidden;
    }

}
