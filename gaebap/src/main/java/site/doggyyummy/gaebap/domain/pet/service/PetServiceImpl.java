package site.doggyyummy.gaebap.domain.pet.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenResponseDTO;
import site.doggyyummy.gaebap.domain.forbidden.repository.ForbiddenRepository;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.pet.dto.PetRequestDTO;
import site.doggyyummy.gaebap.domain.pet.dto.PetResponseDTO;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.pet.repository.PetRepository;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;

import site.doggyyummy.gaebap.domain.recipe.repository.IngredientRepository;
import site.doggyyummy.gaebap.global.security.util.SecurityUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final IngredientRepository ingredientRepository;

    private  final ForbiddenRepository forbiddenRepository;
    //aws
    private final AmazonS3 awsS3Client;

    @Override
    @Transactional
    public void create(PetRequestDTO dto, MultipartFile petImage) throws IOException{
        Member member=new Member();
        member.setId(SecurityUtil.getCurrentLoginMember().getId());
        Pet pet = dto.toEntity();
        pet.setMember(member);
        List<Long> forbiddenList = dto.getForbiddenIngredients();
        for (Long forbiddenId : forbiddenList){
            Ingredient ingredient = Ingredient.builder().id(forbiddenId).build();
            Forbidden forbidden = Forbidden.builder().ingredient(ingredient).pet(pet).build();
            pet.getForbiddens().add(forbidden);
        }

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
        for (PetResponseDTO petDTO : petDTOs){
            List<ForbiddenResponseDTO> forbiddenResponseDTO =petDTO.getForbiddens();
            for(ForbiddenResponseDTO forbiddenDTO :forbiddenResponseDTO ){

                Long ingredientId = forbiddenDTO.getIngredientId();
                Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
                forbiddenDTO.setIngredientName(ingredient.get().getName());
            }
        }

        return petDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public PetResponseDTO selectOne(Long id) {
        PetResponseDTO petDTO = PetResponseDTO.toDTO(petRepository.selectOne(id));
        List<ForbiddenResponseDTO> forbiddenResponseDTO =petDTO.getForbiddens();
        for(ForbiddenResponseDTO forbiddenDTO :forbiddenResponseDTO ){

            Long ingredientId = forbiddenDTO.getIngredientId();
            Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
            forbiddenDTO.setIngredientName(ingredient.get().getName());
        }
        return petDTO;
    }

    @Override
    @Transactional
    public void modify(PetRequestDTO dto,MultipartFile petImage) throws IOException {
        Pet findPet = petRepository.selectOne(dto.getId());

        findPet.setName(dto.getName());


        if(!petImage.isEmpty()) {
            String S3Key = findPet.getS3Key();
            if(S3Key!=null) {
                deleteFile(S3Key);
            }
            Map<String, String> map = uploadFile(petImage);
            S3Key = map.get("s3Key");
            String S3Url = map.get("s3Url");
            findPet.setS3Url(S3Url);
            findPet.setS3Key(S3Key);
        }

        Member memberRef = new Member();
        memberRef.setId(SecurityUtil.getCurrentLoginMember().getId());
        findPet.setMember(memberRef);
        List<Forbidden> removeForbidden = findPet.getForbiddens();
        for (Forbidden forbidden :removeForbidden ){
            forbiddenRepository.delete(forbidden);
        }

        findPet.getForbiddens().clear();


        List<Long> forbiddenList = dto.getForbiddenIngredients();
        for (Long forbiddenId : forbiddenList){
            Ingredient ingredient = Ingredient.builder().id(forbiddenId).build();
            Forbidden forbidden = Forbidden.builder().ingredient(ingredient).pet(findPet).build();
            findPet.getForbiddens().add(forbidden);
            System.out.println(findPet.getForbiddens());
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
