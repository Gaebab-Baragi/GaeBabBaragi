package site.doggyyummy.gaebap.domain.forbidden.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRegisterDTO;
import site.doggyyummy.gaebap.domain.forbidden.repository.ForbiddenRepository;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForbiddenServiceImpl implements ForbiddenService{

    private final ForbiddenRepository forbiddenRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Forbidden> selectByPet(Long petId) {
        List<Forbidden> forbiddens = null;
        try {
            forbiddens = forbiddenRepository.selectByPet(petId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return forbiddens;


    }

    @Override
    @Transactional
    public void create(Long petId, Long IngredientId) {
        Forbidden forbidden = new Forbidden();
        forbidden.getPet().setId(petId);
        forbidden.getIngredient().setId(IngredientId);
        forbiddenRepository.create(forbidden);

    }

    @Override
    @Transactional
    public void delete(Long petId, Long IngredientId) {
        Forbidden forbidden = new Forbidden();
        forbidden.getPet().setId(petId);
        forbidden.getIngredient().setId(IngredientId);
        forbiddenRepository.delete(petId,IngredientId);
    }
}
