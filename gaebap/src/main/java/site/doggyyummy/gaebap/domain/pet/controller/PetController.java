package site.doggyyummy.gaebap.domain.pet.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
@Slf4j
public class PetController {

    private final PetService petService;
    @GetMapping("/{id}")
    public PetResponseDTO selectOne(@PathVariable(name = "id") Long id){
        PetResponseDTO petDTO = petService.selectOne(id);
        return petDTO;
    }
    @GetMapping("")
    public List<PetResponseDTO> selectByMember(){
        List<PetResponseDTO> petDTO = petService.selectByMember(SecurityUtil.getCurrentLoginMember().getId());
        return petDTO;
    }

    @PostMapping(value = "", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void create(@RequestPart(value= "dto") PetRequestDTO dto, @RequestPart(value="petImage", required = false) MultipartFile petImage) throws IOException {
        petService.create(dto, petImage);
    }
    @PostMapping(value = "/modify", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public void modify(@RequestPart PetRequestDTO dto,@RequestPart(required = false) MultipartFile petImage) throws IOException{
        log.info("modify");
        petService.modify(dto,petImage);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id){         //API 명세상으로는 Id를 params로 받지만 회의 때 petId,ingredientId를 받자 했던 거 같아서 일단 그렇게함
        petService.delete(id);
    }
}
