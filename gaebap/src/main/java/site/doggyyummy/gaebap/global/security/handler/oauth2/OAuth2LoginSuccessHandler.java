package site.doggyyummy.gaebap.global.security.handler.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import site.doggyyummy.gaebap.domain.member.dto.response.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.global.security.entity.oauth2.CustomOAuth2User;
import site.doggyyummy.gaebap.global.security.service.JwtService;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MemberRepository memberRepository;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            log.info("onAuthenticationSuccess");
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            loginSuccess(request, response, oAuth2User);
        } catch (Exception e){
           throw e;
        }
    }

    private void loginSuccess(HttpServletRequest request, HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException{
        if (response.isCommitted()) return;

        //새 accessToken & refreshToken을 만들어서 배정함
        String accessToken = jwtService.createAccessToken(oAuth2User.getName());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getName(), refreshToken);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        Member member = memberRepository.findByUsername(oAuth2User.getName()).orElseThrow(() -> new RuntimeException());

        log.info("member : {}", member.toString());
        MemberResponseDTO responseDTO = MemberResponseDTO.toDTO(member);
        String result = objectMapper.writeValueAsString(responseDTO);
        response.getWriter().println(result);
        redirectStrategy.sendRedirect(request, response, makeRedirectUrl(accessToken) );
        log.info("token : {}", accessToken);
    }

    private String makeRedirectUrl(String accessToken){
       return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect/" + accessToken)
               .build().toUriString();
    }
}
