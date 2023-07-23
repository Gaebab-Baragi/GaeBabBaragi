package site.doggyyummy.gaebap.domain.forbidden.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRegisterDTO;
import site.doggyyummy.gaebap.domain.forbidden.repository.ForbiddenRepository;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForbiddenServiceImpl implements ForbiddenService{

    private final ForbiddenRepository forbiddenRepository;


    @Override
    public List<Forbidden> selectByPet(ForbiddenRegisterDTO dto) {
        return null;
    }

    @Override
    public void create(ForbiddenRegisterDTO dto) {

    }

    @Override
    public void delete(ForbiddenRegisterDTO dto) {

    }
}
