package site.doggyyummy.gaebap.domain.forbidden.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.forbidden.repository.ForbiddenRepository;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForbiddenService {

    private final ForbiddenRepository forbiddenRepository;

    @Transactional
    public void create()


}
