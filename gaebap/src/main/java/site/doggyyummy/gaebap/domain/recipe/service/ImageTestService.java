package site.doggyyummy.gaebap.domain.recipe.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestRequestDto;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestResponseDto;
import site.doggyyummy.gaebap.domain.recipe.entity.Image;
import site.doggyyummy.gaebap.domain.recipe.repository.ImageTestRepository;

import java.io.IOException;
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
        String s3Url=uploadFile(multipartFile);
        image.setS3Url(s3Url);
        imageTestRepository.save(image);
        return new ImageTestResponseDto(image);
    }

    //aws s3에 업로드
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String s3FileName= UUID.randomUUID()+"-"+multipartFile.getOriginalFilename();

        ObjectMetadata objMeta=new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());

        awsS3Client.putObject("sh-bucket",s3FileName,multipartFile.getInputStream(),objMeta);
        return awsS3Client.getUrl("sh-bucket",s3FileName).toString();
    }
}
