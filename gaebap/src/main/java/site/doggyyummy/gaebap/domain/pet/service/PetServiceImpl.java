package site.doggyyummy.gaebap.domain.pet.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRequestDTO;
import site.doggyyummy.gaebap.domain.forbidden.repository.ForbiddenRepository;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.dto.PetRequestDTO;
import site.doggyyummy.gaebap.domain.pet.dto.PetResponseDTO;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.pet.repository.PetRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;

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
        Long petMaxId = petRepository.getMaxId();
        List<Long> forbiddenList = dto.getForbiddenIngredients();
        System.out.println("개수 : "  + forbiddenList.size());
        for (Long forbiddenId : forbiddenList){
            Ingredient ingredient = Ingredient.builder().id(forbiddenId).build();
            Forbidden forbidden = Forbidden.builder().ingredient(ingredient).pet(pet).build();
            pet.getForbiddens().add(forbidden);
        }
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
        findPet.setImgUrl(dto.getImgUrl());

        Member memberRef = new Member();
        memberRef.setId(dto.getMemberId());
        findPet.setMember(memberRef);

        List<Long> forbiddenList = dto.getForbiddenIngredients();
        for (Long forbiddenId : forbiddenList){
            Ingredient ingredient = Ingredient.builder().id(forbiddenId).build();
            Forbidden forbidden = Forbidden.builder().ingredient(ingredient).pet(findPet).build();
            findPet.getForbiddens().clear();
            findPet.getForbiddens().add(forbidden);
        }
    }
    @Override
    @Transactional
    public void delete(Long id) {
        petRepository.delete(id);
    }
}
