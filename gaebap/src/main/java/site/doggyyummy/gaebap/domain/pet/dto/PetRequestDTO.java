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
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PetRequestDTO {
    Long id;
    @JsonProperty("member_id")
    Long memberId;
    LocalDateTime birthdate;
    Double weight;
    String name;
    String imgUrl;
    List<ForbiddenRequestDTO> forbiddens;


    public Pet toEntity() {
        Pet pet = new Pet();

        Member member = new Member();
        member.setId(this.memberId);

        pet.setMember(member);
        pet.setBirthDate(this.birthdate);
        pet.setWeight(this.weight);
        pet.setName(this.name);
        pet.setImgUrl(this.imgUrl);

        if (this.forbiddens != null) {
            List<Forbidden> forbiddens = this.forbiddens.stream()
                    .map(ForbiddenRequestDTO::toEntity)
                    .collect(Collectors.toList());
            pet.setForbiddens(forbiddens);
        }

        return pet;
    }

}
