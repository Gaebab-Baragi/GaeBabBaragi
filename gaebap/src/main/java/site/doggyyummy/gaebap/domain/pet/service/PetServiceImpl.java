package site.doggyyummy.gaebap.domain.pet.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRequestDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.dto.PetRequestDTO;
import site.doggyyummy.gaebap.domain.pet.dto.PetResponseDTO;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.pet.repository.PetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;


    @Override
    @Transactional
    public void create(PetRequestDTO dto) {
        Pet pet = dto.toEntity();
        petRepository.create(pet);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PetResponseDTO> selectByMember (Long memberId) {
        List<Pet> pets = petRepository.selectByMember(memberId);
        List<PetResponseDTO> petDTOs = pets.stream()
                .map(PetResponseDTO::toDTO)
                .collect(Collectors.toList());
        return petDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public PetResponseDTO selectOne(Long id) {
        PetResponseDTO dto = PetResponseDTO.toDTO(petRepository.selectOne(id));
        return dto;
    }

    @Override
    @Transactional
    public void modify(PetRequestDTO dto) {
        Pet findPet = petRepository.selectOne(dto.getId());

        findPet.setName(dto.getName());
        findPet.setBirthDate(dto.getBirthdate());
        findPet.setWeight(dto.getWeight());
        findPet.setImgUrl(dto.getImgUrl());

        Member memberRef = new Member();
        memberRef.setId(dto.getMemberId());
        findPet.setMember(memberRef);


        if(dto.getForbiddens() != null && !dto.getForbiddens().isEmpty()) {
            List<Forbidden> forbiddens = dto.getForbiddens().stream()
                    .map(ForbiddenRequestDTO::toEntity)
                    .collect(Collectors.toList());
            findPet.setForbiddens(forbiddens);
        }


    }

    @Override
    @Transactional
    public void delete(Long id) {
        petRepository.delete(id);
    }
}
