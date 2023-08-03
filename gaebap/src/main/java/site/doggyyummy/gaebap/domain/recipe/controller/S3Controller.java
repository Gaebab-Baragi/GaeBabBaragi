package site.doggyyummy.gaebap.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestReqDto;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestRequestDto;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestResponseDto;
import site.doggyyummy.gaebap.domain.recipe.service.ImageTestService;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class S3Controller {

    private final ImageTestService imageTestService;
    @PostMapping("/image/test")
    public ImageTestResponseDto uploadFile(@RequestPart ImageTestRequestDto imageTestRequestDto, @RequestPart("img")MultipartFile multipartFile) throws IOException {
        return imageTestService.saveImage(imageTestRequestDto,multipartFile);
    }

    @DeleteMapping("/image/{id}")
    public String deleteFile(@PathVariable Long id){
        imageTestService.delete(id);
        return "ok";
    }

    @PutMapping("/image/{id}")
    public String modifyFile(@PathVariable Long id,@RequestPart ImageTestRequestDto imageTestRequestDto, @RequestPart("img")MultipartFile multipartFile){
        try {
            imageTestService.modify(id, imageTestRequestDto, multipartFile);
        }catch (Exception e){
            System.out.println("$$$$$$$$$$$$$$");
            System.out.println(e.getMessage());
        }
        return "ok";
    }


}
