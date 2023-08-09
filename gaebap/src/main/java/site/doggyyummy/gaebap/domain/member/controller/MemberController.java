package site.doggyyummy.gaebap.domain.member.controller;

import com.nimbusds.jose.proc.SecurityContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.member.dto.request.MemberModifyDTO;
import site.doggyyummy.gaebap.domain.member.dto.request.MemberRegisterDTO;
import site.doggyyummy.gaebap.domain.member.dto.response.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.exception.custom.NoSuchUserException;
import site.doggyyummy.gaebap.domain.member.exception.custom.NoSuchUsernameException;
import site.doggyyummy.gaebap.domain.member.service.MemberMailService;
import site.doggyyummy.gaebap.domain.member.service.MemberService;
import site.doggyyummy.gaebap.global.security.util.SecurityUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Slf4j
@Schema(description = "회원 관리 컨트롤러")
public class MemberController {

    private final MemberService memberService;
    private final MemberMailService memberMailService;

    @GetMapping("")
    @Operation(description = "현재 로그인 중인 회원 정보를 조회")
    public ResponseEntity<MemberResponseDTO> findCurrentLoginMember() throws NoSuchUsernameException {
       return new ResponseEntity<>(MemberResponseDTO.toDTO(SecurityUtil.getCurrentLoginMember())
                                , HttpStatus.OK);
    }

    //=================================================================================

    @PostMapping("/register")
    @Operation(description = "회원 가입")
    public ResponseEntity<String> signUp(@RequestBody MemberRegisterDTO registerDTO) throws Exception{
        log.info("registerDTO : {}", registerDTO);
        memberService.signUp(MemberRegisterDTO.toEntity(registerDTO));
        return new ResponseEntity<>("로그인에 성공했습니다.", HttpStatus.CREATED);
    }

    //=================================================================================

    @PostMapping("/register/username")
    @Operation(description = "회원 가입 시 이메일의 유효성을 검사")
    public ResponseEntity<String> validateRegisterUsername(@RequestBody MemberRegisterDTO registerDTO) throws Exception {
        memberService.validateRegistrationUsername(registerDTO.getRegisterName());
        return new ResponseEntity<>(memberMailService.sendEmail(registerDTO.getRegisterName()), HttpStatus.OK);
    }

    @PostMapping("/register/nickname")
    @Operation(description = "회원 가입 시 닉네임 유효성을 검사")
    public ResponseEntity<String> validateRegisterNickname(@RequestBody MemberRegisterDTO registerDTO) throws Exception {
        memberService.validateRegistrationNickname(registerDTO.getNickname());
        return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }

    //=================================================================================
    @PutMapping(value = "/modify", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(description = "회원 정보 수정")
    public ResponseEntity<String> modify(@RequestPart(value="dto") MemberModifyDTO modifyDTO, @RequestPart(value="file", required = false) MultipartFile file) throws Exception{
        memberService.modify(MemberModifyDTO.toEntity(modifyDTO), file);
        return new ResponseEntity<>("modified successfully", HttpStatus.OK);
    }

    @PostMapping("/modify/nickname")
    @Operation(description = "회원 정보 수정 시 닉네임을 검사")
    public ResponseEntity<String> validateModifyNickname(@RequestBody MemberModifyDTO modifyDTO) throws Exception{
        memberService.validateNicknameModification(MemberModifyDTO.toEntity(modifyDTO));
        return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }

    //====================================================================================

    @GetMapping("/test")
    public ResponseEntity<MemberResponseDTO> test() throws NoSuchUserException {
        Member member = SecurityUtil.getCurrentLoginMember();
        MemberResponseDTO dto = MemberResponseDTO.toDTO((member));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
