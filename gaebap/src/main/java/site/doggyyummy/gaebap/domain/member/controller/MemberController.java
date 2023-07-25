package site.doggyyummy.gaebap.domain.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.member.dto.request.MemberModifyDTO;
import site.doggyyummy.gaebap.domain.member.dto.request.MemberRegisterDTO;
import site.doggyyummy.gaebap.domain.member.dto.response.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.service.MemberMailService;
import site.doggyyummy.gaebap.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberMailService memberMailService;

    @GetMapping("")
    public MemberResponseDTO findMemberByName(@Param(value = "id") String memberId) throws Exception{
       return MemberResponseDTO.toDTO(memberService.findByName(memberId).orElseThrow(() -> new Exception()));
    }

    //=================================================================================

    @PostMapping("/register")
    public String signUp(@RequestBody MemberRegisterDTO registerDTO) throws Exception{
        log.info("registerDTO : {}", registerDTO);
        memberService.signUp(MemberRegisterDTO.toEntity(registerDTO));
        return "reg";
    }

    //=================================================================================

    @PostMapping("/register/id")
    public boolean validateRegisterId(@RequestBody MemberRegisterDTO registerDTO) {
        return memberService.isValidRegistrationName(registerDTO.getRegisterName());
    }

    @PostMapping("/register/email")
    public String validateRegisterEmail(@RequestBody MemberRegisterDTO registerDTO) throws Exception {
        if (memberService.isValidRegistrationEmail(registerDTO.getEmail())){
            try {
                return memberMailService.sendEmail(registerDTO.getEmail());
            }
            catch (Exception e){
                throw new Exception("invalid email");
            }
        }
        throw new Exception("duplicate email");
    }

    @PostMapping("/register/nickname")
    public boolean validateRegisterNickname(@RequestBody MemberRegisterDTO registerDTO) {
        return memberService.isValidRegistrationNickname(registerDTO.getNickname());
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

    //====================================================================================


}
