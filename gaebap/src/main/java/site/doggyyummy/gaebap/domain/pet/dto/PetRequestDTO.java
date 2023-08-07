package site.doggyyummy.gaebap.domain.pet.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRequestDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetRequestDTO {
    Long id;
    String name;

    List<Long> forbiddenIngredients = new ArrayList<>();


    public Pet toEntity() {
        Pet pet = new Pet();

        Member member = new Member();
        pet.setMember(member);
        pet.setName(this.name);

        return pet;
    }

}
