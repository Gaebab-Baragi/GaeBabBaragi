package site.doggyyummy.gaebap.domain.recipe.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestReqDto;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestRequestDto;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestResponseDto;
import site.doggyyummy.gaebap.domain.recipe.entity.Image;
import site.doggyyummy.gaebap.domain.recipe.repository.ImageTestRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageTestService {
    private final ImageTestRepository imageTestRepository;
    //AWS S3
    private final AmazonS3 awsS3Client;
    @Transactional
    public ImageTestResponseDto saveImage(ImageTestRequestDto reqDto,MultipartFile multipartFile) throws IOException{
        Image image=new Image();
        image.setName(reqDto.getName());
        imageTestRepository.save(image);

        Map<String,String> map=uploadFile(image,multipartFile);
        String s3Key=map.get("s3Key");
        String s3Url=map.get("s3Url");
        image.setS3Key(s3Key);
        image.setS3Url(s3Url);
        imageTestRepository.save(image);
        
        return new ImageTestResponseDto(image);
    }

    @Transactional
    public void delete(Long id){
        Optional<Image> img=imageTestRepository.findById(id);
        Image image=img.get();
        deleteFile(image);
        imageTestRepository.delete(image);
    }

    @Transactional
    public void modify(Long id,ImageTestRequestDto reqDto,MultipartFile multipartFile) throws IOException {
        Optional<Image> img=imageTestRepository.findById(id);
        Image image=img.get();
        Map<String,String> res=modifyFile(image,multipartFile);
        image.setS3Key(res.get("s3Key"));
        image.setS3Url(res.get("s3Url"));
        imageTestRepository.save(image);
    }

    //aws s3에 업로드
    public Map<String, String> uploadFile(Object object,MultipartFile multipartFile) throws IOException {
        String s3Key="";
        if(object instanceof Image){
            s3Key="image/"+((Image)object).getId()+"/"+UUID.randomUUID()+"-"+multipartFile.getOriginalFilename();
        }
        ObjectMetadata objMeta=new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(multipartFile.getInputStream().available());

        awsS3Client.putObject("sh-bucket",s3Key,multipartFile.getInputStream(),objMeta);
        String s3Url=awsS3Client.getUrl("sh-bucket",s3Key).toString();
        Map<String,String> returnMap=new HashMap<>();
        returnMap.put("s3Key",s3Key);
        returnMap.put("s3Url",s3Url);
        return returnMap;
    }

    //aws s3에 객체 삭제 (아예 해당 id를 삭제)
    public void deleteFile(Image image){
        String s3Key=image.getS3Key();
        awsS3Client.deleteObject("sh-bucket",s3Key);
    }

    //aws s3에 해당 id의 이미지만 수정 (해당 이미지를 삭제하고 수정된 이미지 업로드)
    public Map<String,String> modifyFile(Image image,MultipartFile multipartFile) throws IOException {
        deleteFile(image);
        return uploadFile(image, multipartFile);
    }

    //aws s3에 저장되는 객체 url
    //https://버킷이름.s3.ap-northeast-2.amazonaws.com/profile인지recipe인지/1be20bde-97e5-3abc-b771-73a9497428d0
    //recipe이면
    //https://버킷이름.s3.ap-northeast-2.amazonaws.com/recipe/레시피ID/어쩌고~
    //key는
    //recipe/레시피ID/어쩌고~
}
