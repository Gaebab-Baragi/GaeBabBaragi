package site.doggyyummy.gaebap.domain.pet.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.pet.dto.PetRequestDTO;
import site.doggyyummy.gaebap.domain.pet.dto.PetResponseDTO;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.pet.service.PetService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forbidden")
public class PetController {

    PetService petService;
    @GetMapping("")
    public PetResponseDTO selectOne(@RequestParam(name= "id") Long id){
        PetResponseDTO petDTO = petService.selectOne(id);
        return petDTO;
    }
    @GetMapping("")
    public List<PetResponseDTO> selectByMember(@RequestParam(name= "member_id") Long memberId){
        List<PetResponseDTO> pets = petService.selectByMember(memberId);
        return pets;
    }


    @PostMapping("")
    public void create(PetRequestDTO dto) {
        petService.create(dto);
    }
    @PutMapping("")
    public void modify(PetRequestDTO dto){
        petService.modify(dto);
    }
    @DeleteMapping("")
    public void delete(Long id){         //API 명세상으로는 Id를 params로 받지만 회의 때 petId,ingredientId를 받자 했던 거 같아서 일단 그렇게함
        petService.delete(id);
    }
}
