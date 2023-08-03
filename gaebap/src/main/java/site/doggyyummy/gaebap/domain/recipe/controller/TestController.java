package site.doggyyummy.gaebap.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.doggyyummy.gaebap.domain.recipe.dto.TestRequestDto;
import site.doggyyummy.gaebap.domain.recipe.service.TestService;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final TestService testService;

    @ResponseBody
    @GetMapping("/test")
    public String save(@RequestBody TestRequestDto reqDto){
        testService.save(reqDto);
        return "ok";
    }
}
