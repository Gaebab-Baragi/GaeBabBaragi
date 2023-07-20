package site.doggyyummy.gaebap.domain.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.member.domain.Member;
import site.doggyyummy.gaebap.domain.member.dto.MemberRegisterDTO;
import site.doggyyummy.gaebap.domain.member.dto.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired MemberService memberService;
    @PostMapping("/register")
    public String signUp(@RequestBody MemberRegisterDTO registerDTO){
        try {
            memberService.signUp(MemberRegisterDTO.toMember(registerDTO));
            Member m = memberService.findByName("pysss").get();
            System.out.println(m.toString());
            return "reg";
        }
        catch (Exception e){
            return "exception";
        }
    }

    @GetMapping("")
    public MemberResponseDTO memberResponseDTO(@RequestParam String name){
        try {
            Member member = memberService.findByName(name).get();
            return MemberResponseDTO.toDTO(member);
        }
        catch (Exception e) {
            System.out.println("no member found");
            return null;
        }
    }
}
