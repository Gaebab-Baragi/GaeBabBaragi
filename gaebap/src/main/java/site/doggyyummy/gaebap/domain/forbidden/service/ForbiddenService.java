package site.doggyyummy.gaebap.domain.forbidden.service;

import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRegisterDTO;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;

public interface ForbiddenService {
    List<Forbidden> selectByPet (ForbiddenRegisterDTO dto);
    void create(ForbiddenRegisterDTO dto);
    void delete(ForbiddenRegisterDTO dto);




}
