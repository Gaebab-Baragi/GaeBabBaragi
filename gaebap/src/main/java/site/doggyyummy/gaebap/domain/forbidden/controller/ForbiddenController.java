package site.doggyyummy.gaebap.domain.forbidden.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRequestDTO;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenResponseDTO;
import site.doggyyummy.gaebap.domain.forbidden.service.ForbiddenServiceImpl;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forbidden")
public class ForbiddenController {

    private final ForbiddenServiceImpl forbiddenService;

    @GetMapping("/{pet_id}")
    public List<ForbiddenResponseDTO> selectByPet(@PathVariable(name= "pet_id") Long petId){
        List<ForbiddenResponseDTO> forbiddens = forbiddenService.selectByPet(petId);
        return forbiddens;
    }

    @PostMapping("")
    public void create(@RequestBody ForbiddenRequestDTO forbiddenRequestDTO) {
        forbiddenService.create(forbiddenRequestDTO);
    }

    @DeleteMapping("")     //id를 프론트단에서 알기 힘드므로 pet_id,ingredient_id를 받음
    public void delete(@RequestBody ForbiddenRequestDTO forbiddenRequestDTO){         //API 명세상으로는 Id를 params로 받지만 회의 때 petId,ingredientId를 받자 했던 거 같아서 일단 그렇게함
        forbiddenService.delete(forbiddenRequestDTO);
    }

}
