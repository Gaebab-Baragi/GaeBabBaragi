package site.doggyyummy.gaebap.domain.forbidden.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.forbidden.service.ForbiddenServiceImpl;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forbidden")
public class ForbiddenController {

    ForbiddenServiceImpl forbiddenService;

    @GetMapping("/{pet_id}")
    public List<Forbidden> selectByPet(@PathVariable(name= "pet_id") Long petId){
        List<Forbidden> forbiddens = forbiddenService.selectByPet(petId);
        return forbiddens;
    }

    @PostMapping("")
    public void create(Long petId, Long ingredientId) {
        forbiddenService.create(petId,ingredientId);
    }

    @DeleteMapping("")     //id를 프론트단에서 알기 힘드므로 pet_id,ingredient_id를 받음
    public void delete(Long petId, Long ingredientId){         //API 명세상으로는 Id를 params로 받지만 회의 때 petId,ingredientId를 받자 했던 거 같아서 일단 그렇게함
        forbiddenService.delete(petId,ingredientId);
    }

}
