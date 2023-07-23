package site.doggyyummy.gaebap.domain.forbidden.service;

import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;

public interface ForbiddenService {
    void create(Long petId, Long IngredientId);
    List<Forbidden> selectByPet (Long petId);

    void delete(Long petId, Long IngredientId);

}
