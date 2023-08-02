package site.doggyyummy.gaebap.global.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.doggyyummy.gaebap.domain.member.dto.response.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.exception.custom.NoSuchUserException;
import site.doggyyummy.gaebap.domain.member.service.MemberService;
import site.doggyyummy.gaebap.global.security.service.JwtService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class Oauth2Controller {

    private final JwtService jwtService;
    private final MemberService memberService;

    @GetMapping("/oauth2/success")
    public ResponseEntity<MemberResponseDTO> getMemberDTOByAccessToken(String token) throws Exception{
        log.info("accessToken : {}", token);
        String name = jwtService.extractName(token).orElseThrow(() -> new Exception());
        Member member = memberService.findByName(name).orElseThrow(() -> new NoSuchUserException());
        //memberService.uploadImageByUrl(member);
        log.info("send member_info : {}", member.toString());
        return new ResponseEntity<>(MemberResponseDTO.toDTO(member), HttpStatus.OK);
    }
}
