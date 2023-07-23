package site.doggyyummy.gaebap.domain.forbidden.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.doggyyummy.gaebap.domain.forbidden.dto.ForbiddenRegisterDTO;
import site.doggyyummy.gaebap.domain.forbidden.service.ForbiddenServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forbidden")
public class ForbiddenController {

    ForbiddenServiceImpl forbiddenService;

    @GetMapping("")
    public List<ForbiddenRegisterDTO> getForbiddensByMettingID(@RequestParam(name= "member_id") Long member_id){

    }


}
