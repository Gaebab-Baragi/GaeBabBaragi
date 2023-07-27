package site.doggyyummy.gaebap.global.security.handler.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.global.security.entity.oauth2.CustomOAuth2User;
import site.doggyyummy.gaebap.global.security.service.JwtService;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            //TODO 첫 로그인의 경우는 생각해봐야 됨 -> 필요한 정보 채우는 페이지로
            String accessToken = jwtService.createAccessToken(oAuth2User.getName());
            response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
            response.sendRedirect("oauth2/sign-up"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트

            jwtService.sendAccessAndRefreshToken(response, accessToken, null);
            //loginSuccess(response, oAuth2User);
        } catch (Exception e){
           throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User){
        String accessToken = jwtService.createAccessToken(oAuth2User.getName());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), jwtService.getBearer() + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), jwtService.getBearer() + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getName(), refreshToken);
    }
}
