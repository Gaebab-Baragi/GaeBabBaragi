package site.doggyyummy.gaebap.domain.pet.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.dto.PetRequestDTO;
import site.doggyyummy.gaebap.domain.pet.dto.PetResponseDTO;
import site.doggyyummy.gaebap.domain.pet.service.PetService;
import site.doggyyummy.gaebap.global.security.util.SecurityUtil;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetController {

    private final PetService petService;
    @GetMapping("/{id}")
    public PetResponseDTO selectOne(@PathVariable(name = "id") Long id){
        PetResponseDTO petDTO = petService.selectOne(id);
        return petDTO;
    }
    @GetMapping("")
    public List<PetResponseDTO> selectByMember(@RequestParam(name= "member_id") Long memberId){
        List<PetResponseDTO> petDTO = petService.selectByMember(memberId);
        return petDTO;
    }

    @PostMapping("")
    public void create(@RequestPart PetRequestDTO dto, @RequestPart MultipartFile petImage) throws IOException {
        petService.create(dto,petImage);
    }
    @PutMapping("")
    public void modify(@RequestPart PetRequestDTO dto,@RequestPart MultipartFile petImage) throws IOException{
        petService.modify(dto,petImage);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id){         //API 명세상으로는 Id를 params로 받지만 회의 때 petId,ingredientId를 받자 했던 거 같아서 일단 그렇게함
        petService.delete(id);
    }
}
