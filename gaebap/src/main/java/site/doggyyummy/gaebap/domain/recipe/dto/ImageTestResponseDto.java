package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.recipe.entity.Image;

@Getter
@Setter
public class ImageTestResponseDto {
    private String name;

    public ImageTestResponseDto(Image image){
        this.name= image.getName();
    }
}
