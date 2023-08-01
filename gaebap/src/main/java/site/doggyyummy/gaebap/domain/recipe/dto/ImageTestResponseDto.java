package site.doggyyummy.gaebap.domain.recipe.dto;

import lombok.Getter;
import lombok.Setter;
import site.doggyyummy.gaebap.domain.recipe.entity.Image;

@Getter
@Setter
public class ImageTestResponseDto {
    private String name;
    private String s3Url;

    public ImageTestResponseDto(Image image){
        this.name= image.getName();
        this.s3Url= image.getS3Url();
    }
}
