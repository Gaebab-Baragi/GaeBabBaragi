package site.doggyyummy.gaebap.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.member.dto.request.MemberModifyDTO;
import site.doggyyummy.gaebap.domain.member.dto.request.MemberRegisterDTO;
import site.doggyyummy.gaebap.domain.member.dto.response.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.exception.custom.NoSuchUsernameException;
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
    public ResponseEntity<MemberResponseDTO> findByName(String username) throws NoSuchUsernameException {
       return new ResponseEntity<>(MemberResponseDTO.toDTO(memberService.findByName(username).orElseThrow(() -> new NoSuchUsernameException()))
                                , HttpStatus.OK);
    }

    //=================================================================================

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody MemberRegisterDTO registerDTO) throws Exception{
        log.info("registerDTO : {}", registerDTO);
        memberService.signUp(MemberRegisterDTO.toEntity(registerDTO));
        return new ResponseEntity<>("로그인에 성공했습니다.", HttpStatus.CREATED);
    }

    //=================================================================================

    @PostMapping("/register/username")
    public ResponseEntity<String> validateRegisterUsername(@RequestBody MemberRegisterDTO registerDTO) throws Exception {
        memberService.validateRegistrationUsername(registerDTO.getRegisterName());
        return new ResponseEntity<>(memberMailService.sendEmail(registerDTO.getRegisterName()), HttpStatus.OK);
    }

    @PostMapping("/register/nickname")
    public ResponseEntity<String> validateRegisterNickname(@RequestBody MemberRegisterDTO registerDTO) throws Exception {
        memberService.validateRegistrationNickname(registerDTO.getNickname());
        return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }

    //=================================================================================
    @PutMapping("/modify")
    public ResponseEntity<String> modify(@RequestBody MemberModifyDTO modifyDTO) throws Exception{
        memberService.modify(MemberModifyDTO.toEntity(modifyDTO), modifyDTO.getFile(), modifyDTO.getFileType());
        return new ResponseEntity<>("회원 정보 수정에 성공했습니다.", HttpStatus.OK);
    }

    @PostMapping("/modify/nickname")
    public ResponseEntity<String> validateModifyNickname(@RequestBody MemberModifyDTO modifyDTO) throws Exception{
        memberService.validateNicknameModification(MemberModifyDTO.toEntity(modifyDTO));
        return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }

    //====================================================================================


}
