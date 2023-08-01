package site.doggyyummy.gaebap.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestRequestDto;
import site.doggyyummy.gaebap.domain.recipe.dto.ImageTestResponseDto;
import site.doggyyummy.gaebap.domain.recipe.service.ImageTestService;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class S3Controller {

    private final ImageTestService imageTestService;
    @PostMapping("/image/test")
    public ImageTestResponseDto uploadFile(@RequestBody ImageTestRequestDto imageTestRequestDto, @RequestParam("img")MultipartFile multipartFile) throws IOException {
        return imageTestService.saveImage(imageTestRequestDto,multipartFile);
    }
}
