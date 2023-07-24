package site.doggyyummy.gaebap.domain.forbidden.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRequestDTO;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenResponseDTO;
import site.doggyyummy.gaebap.domain.forbidden.repository.ForbiddenRepository;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForbiddenServiceImpl implements ForbiddenService{

    private final ForbiddenRepository forbiddenRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ForbiddenResponseDTO> selectByPet(Long petId) {
        List<Forbidden> forbiddens = null;
        List<ForbiddenResponseDTO> forbiddensDTO = null;
        try {
            forbiddens = forbiddenRepository.selectByPet(petId);
            forbiddensDTO = forbiddens.stream()
                    .map(ForbiddenResponseDTO::toDTO)
                    .collect(Collectors.toList());
            return forbiddensDTO;

        }catch (Exception e){
            e.printStackTrace();
        }
        return forbiddensDTO;
    }

    @Override
    @Transactional
    public void create(ForbiddenRequestDTO forbiddenRequestDTO) {
        Forbidden forbidden = forbiddenRequestDTO.toEntity();
        System.out.println(forbidden.getIngredient().getId());
        forbiddenRepository.create(forbidden);
    }

    @Override
    @Transactional
    public void delete(ForbiddenRequestDTO forbiddenRequestDTO) {
        Forbidden forbidden = forbiddenRequestDTO.toEntity();

        forbiddenRepository.delete(forbidden);
    }
}
