package site.doggyyummy.gaebap.global.security.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.doggyyummy.gaebap.domain.member.dto.response.MemberResponseDTO;

@Controller
public class Oauth2Controller {

    @GetMapping("/oauth2/success")
    public ResponseEntity<MemberResponseDTO> getMemberDTOByAccessToken(@Param(""))


}
