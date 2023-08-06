package site.doggyyummy.gaebap.domain.pet.service;

import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.pet.dto.PetRequestDTO;
import site.doggyyummy.gaebap.domain.pet.dto.PetResponseDTO;

import java.io.IOException;
import java.util.List;

public interface PetService {
    void create(PetRequestDTO dto, MultipartFile petImage) throws IOException;
    List<PetResponseDTO> selectByMember (Long memberId);
    PetResponseDTO selectOne(Long Id);
    void modify(PetRequestDTO dto,MultipartFile petImage) throws IOException;

    void delete(Long id);

}
