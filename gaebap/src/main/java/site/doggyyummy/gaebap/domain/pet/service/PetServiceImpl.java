package site.doggyyummy.gaebap.domain.pet.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRequestDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.dto.PetRequestDTO;
import site.doggyyummy.gaebap.domain.pet.dto.PetResponseDTO;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.pet.repository.PetRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;
import site.doggyyummy.gaebap.domain.recipe.entity.Step;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    //aws
    private final AmazonS3 awsS3Client;

    @Override
    @Transactional
    public void create(PetRequestDTO dto, MultipartFile petImage) throws IOException{
        Member member=new Member();
        member.setId(dto.getMemberId());
        Pet pet = dto.toEntity();
        petRepository.create(pet);

        Map<String,String> map=uploadFile(petImage);
        String S3Key=map.get("s3Key");
        String S3Url=map.get("s3Url");
        pet.setS3Key(S3Key);
        pet.setS3Url(S3Url);
        petRepository.create(pet);

    }


    @Override
    @Transactional(readOnly = true)
    public List<PetResponseDTO> selectByMember (Long memberId) {
        List<Pet> pets = petRepository.selectByMember(memberId);
        List<PetResponseDTO> petDTOs = pets.stream()
                .map(PetResponseDTO::toDTO)
                .collect(Collectors.toList());
        return petDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public PetResponseDTO selectOne(Long id) {
        PetResponseDTO dto = PetResponseDTO.toDTO(petRepository.selectOne(id));
        return dto;
    }

    @Override
    @Transactional
    public void modify(PetRequestDTO dto,MultipartFile petImage) throws IOException {
        Pet findPet = petRepository.selectOne(dto.getId());

        findPet.setName(dto.getName());
        findPet.setBirthDate(dto.getBirthdate());
        findPet.setWeight(dto.getWeight());

        if(!petImage.isEmpty()) {
            String S3Key = findPet.getS3Key();
            if(S3Key!=null || S3Key.equals("")) {
                deleteFile(S3Key);
            }
            Map<String, String> map = uploadFile(petImage);
            S3Key = map.get("s3Key");
            String S3Url = map.get("s3Url");
            findPet.setS3Url(S3Url);
            findPet.setS3Key(S3Key);
        }

        Member memberRef = new Member();
        memberRef.setId(dto.getMemberId());
        findPet.setMember(memberRef);


        if(dto.getForbiddens() != null && !dto.getForbiddens().isEmpty()) {
            List<Forbidden> forbiddens = dto.getForbiddens().stream()
                    .map(ForbiddenRequestDTO::toEntity)
                    .collect(Collectors.toList());
            findPet.setForbiddens(forbiddens);
        }


    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pet pet=petRepository.selectOne(id);
        String S3Key=pet.getS3Key();
        deleteFile(S3Key);
        petRepository.delete(id);
    }


    //aws s3 펫 사진 등록
    public Map<String, String> uploadFile(MultipartFile multipartFile) throws IOException {
        String s3Key = "";
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        s3Key = "pet/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(multipartFile.getInputStream().available());

        awsS3Client.putObject("sh-bucket", s3Key, multipartFile.getInputStream(), objMeta);
        String s3Url = awsS3Client.getUrl("sh-bucket", s3Key).toString();
        Map<String, String> map = new HashMap<>();
        map.put("s3Key", s3Key);
        map.put("s3Url", s3Url);
        return map;
    }
    //aws s3 펫 사진 삭제
    public void deleteFile(String S3Key) {
        awsS3Client.deleteObject("sh-bucket", S3Key);
    }
}
