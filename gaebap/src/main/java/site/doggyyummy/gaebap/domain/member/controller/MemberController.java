package site.doggyyummy.gaebap.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MemberController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
