package site.doggyyummy.gaebap.global.security.handler.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import site.doggyyummy.gaebap.domain.member.dto.response.MemberResponseDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.entity.Role;
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

    @Value("${current.front}")
    private String frontUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            log.info("onAuthenticationSuccess");
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            if (oAuth2User.getRole() == Role.GUEST){
                guestLogin(request, response, oAuth2User);
            }
            else loginSuccess(request, response, oAuth2User);
        } catch (Exception e){
           log.info("onAuthenticationSuccess: {}", e.getMessage());
        }
    }

    private void loginSuccess(HttpServletRequest request, HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException{
        if (response.isCommitted()) return;

        String accessToken = jwtService.createAccessToken(oAuth2User.getName());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        log.info("새 로그인 유저 : {}, 리프레시 토큰 : {}", oAuth2User.getName(), refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getName(), refreshToken);

        Member member = memberRepository.findByUsername(oAuth2User.getName()).orElseThrow(() -> new RuntimeException());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        MemberResponseDTO responseDTO = MemberResponseDTO.toDTO(member);
        String result = objectMapper.writeValueAsString(responseDTO);
        response.getWriter().println(result);

        String redirectUrl = UriComponentsBuilder.fromUriString(frontUrl+"/oauth2/redirect/" + accessToken)
                .build().toUriString();

        redirectStrategy.sendRedirect(request, response, redirectUrl);
        log.info("token : {}", accessToken);
    }

    private void guestLogin(HttpServletRequest request, HttpServletResponse response, CustomOAuth2User oAuth2User) throws Exception {
        Member member = oAuth2User.getMember();
        String accessToken = jwtService.createAccessToken(member.getUsername());
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, null);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        MemberResponseDTO responseDTO = MemberResponseDTO.toDTO(member);
        String result = objectMapper.writeValueAsString(responseDTO);
        response.getWriter().println(result);

        String redirectUrl = UriComponentsBuilder.fromUriString(frontUrl+"/oauth2/signup")
                .build().toUriString();

        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }









}
