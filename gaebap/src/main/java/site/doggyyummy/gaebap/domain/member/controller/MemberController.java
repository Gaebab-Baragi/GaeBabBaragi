package site.doggyyummy.gaebap.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.token.Token;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.member.dto.MemberLoginDTO;
import site.doggyyummy.gaebap.domain.member.dto.MemberModifyDTO;
import site.doggyyummy.gaebap.domain.member.dto.MemberRegisterDTO;
import site.doggyyummy.gaebap.domain.member.dto.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public MemberResponseDTO findMemberByName(@Param(value = "id") String memberId) throws Exception{
       return MemberResponseDTO.toDTO(memberService.findByName(memberId).orElseThrow(() -> new Exception()));
    }

    //=================================================================================

    @PostMapping("/register")
    public String signUp(@RequestBody MemberRegisterDTO registerDTO) throws Exception{
        memberService.signUp(MemberRegisterDTO.toEntity(registerDTO));
        return "reg";
    }

    //=================================================================================

    @PostMapping("/register/id")
    public boolean validateRegisterId(@RequestBody MemberRegisterDTO registerDTO) {
        return memberService.isDuplicateName(registerDTO.getRegisterName());
    }

    @PostMapping("/register/email")
    public boolean validateRegisterEmail(@RequestBody MemberRegisterDTO registerDTO) {
        return memberService.isDuplicateEmail(registerDTO.getNickname());
    }

    @PostMapping("/register/nickname")
    public boolean validateRegisterNickname(@RequestBody MemberRegisterDTO registerDTO) {
        return memberService.isDuplicateEmail(registerDTO.getEmail());
    }

    //=================================================================================
    @PutMapping("/modify")
    public String modify(@RequestBody MemberModifyDTO modifyDTO) throws Exception{
        memberService.modify(MemberModifyDTO.toEntity(modifyDTO));
        return "modify";
    }

    @PostMapping("/modify/email")
    public boolean validateModifyEmail(@RequestBody MemberModifyDTO modifyDTO) throws Exception {
        return memberService.isValidEmailModification(MemberModifyDTO.toEntity(modifyDTO));
    }

    @PostMapping("/modify/nickname")
    public boolean validateModifyNickname(@RequestBody MemberModifyDTO modifyDTO) throws Exception{
        return memberService.isValidNicknameModification(MemberModifyDTO.toEntity(modifyDTO));
    }

    @GetMapping("/login")
    public String loginForm(HttpServletRequest req){
        String referer = req.getHeader("Referer") ;
        req.getSession().setAttribute("prevPage", referer);//login으로 오기 전의 페이지를 저장해 둠.
        return "member/login";
    }

}
