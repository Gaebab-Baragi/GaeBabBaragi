package site.doggyyummy.gaebap.domain.pet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenResponseDTO;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetResponseDTO {
    Long id;
    Long memberId;
    String name;
    String imgUrl;
    List<ForbiddenResponseDTO> forbiddens;

    public static PetResponseDTO toDTO(Pet pet){
        PetResponseDTO dto = new PetResponseDTO();
        dto.setId(pet.getId());
        dto.setMemberId(pet.getMember().getId());
        dto.setName(pet.getName());
        dto.setImgUrl(pet.getS3Url());
         List<ForbiddenResponseDTO> forbiddensDTO = pet.getForbiddens().stream()
             .map(ForbiddenResponseDTO::toDTO)
             .collect(Collectors.toList());
         dto.setForbiddens(forbiddensDTO);
         return dto;
    }
}
