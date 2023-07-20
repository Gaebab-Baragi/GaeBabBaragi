package site.doggyyummy.gaebap.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.member.domain.Member;
import site.doggyyummy.gaebap.domain.member.dto.MemberRegisterDTO;
import site.doggyyummy.gaebap.domain.member.dto.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    @PostMapping("/register")
    public String signUp(@RequestBody MemberRegisterDTO registerDTO){
        try {
            memberService.signUp(MemberRegisterDTO.toEntity(registerDTO));
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
            return null;
        }
    }
}
