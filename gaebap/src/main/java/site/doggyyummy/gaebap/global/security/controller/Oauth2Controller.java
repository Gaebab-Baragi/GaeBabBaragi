package site.doggyyummy.gaebap.global.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import site.doggyyummy.gaebap.domain.member.exception.custom.AccessTokenInvalidException;
import site.doggyyummy.gaebap.domain.member.exception.custom.NoSuchUserException;
import site.doggyyummy.gaebap.domain.member.service.MemberService;
import site.doggyyummy.gaebap.global.security.service.JwtService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class Oauth2Controller {

    private final JwtService jwtService;
    private final MemberService memberService;

    @GetMapping("/oauth2/success")
    public ResponseEntity<MemberResponseDTO> getMemberDTOByAccessToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String token = jwtService.extractAccessToken(request).orElseThrow(() -> new AccessTokenInvalidException());

        String name = jwtService.extractName(token).orElseThrow(() -> new Exception());
        Member member = memberService.findByName(name).orElseThrow(() -> new NoSuchUserException());
        try {
            memberService.uploadImageByUrl(member);
        }
        catch (Exception e){
            return new ResponseEntity<>(MemberResponseDTO.toDTO(member), HttpStatus.OK);
        }
        return new ResponseEntity<>(MemberResponseDTO.toDTO(member), HttpStatus.OK);
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<String> checkLogin() throws Exception{
        return new ResponseEntity<>("checked", HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletResponse response) throws Exception{
        return new ResponseEntity<>("refreshed", HttpStatus.OK);
    }

}
