package site.doggyyummy.gaebap.domain.pet.service;

import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.pet.dto.PetRequestDTO;
import site.doggyyummy.gaebap.domain.pet.dto.PetResponseDTO;

import java.util.List;

public interface PetService {
    void create(PetRequestDTO dto);
    List<PetResponseDTO> selectByMember (Long memberId);
    PetResponseDTO selectOne(Long Id);
    void modify(PetRequestDTO dto);

    void delete(Long id);

}
