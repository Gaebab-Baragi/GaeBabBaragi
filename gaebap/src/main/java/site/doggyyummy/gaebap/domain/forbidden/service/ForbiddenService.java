package site.doggyyummy.gaebap.domain.forbidden.service;

import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRequestDTO;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenResponseDTO;
import java.util.List;

public interface ForbiddenService {
    void create(ForbiddenRequestDTO forbiddenRequestDTO);
    List<ForbiddenResponseDTO>  selectByPet (Long petId);

    void delete(ForbiddenRequestDTO forbiddenRequestDTO);

}
