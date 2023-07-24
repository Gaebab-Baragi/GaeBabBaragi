package site.doggyyummy.gaebap.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.global.security.service.JwtService;

import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

   /*
    * 로그인 화면으로 오기 전의 페이지를 저장해놓고 거기로 redirect함
    */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String username = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(username);
        String refreshToken = jwtService.createRefreshToken();
        HttpSession session = request.getSession();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        jwtService.updateRefreshToken(username, refreshToken);

        log.info("login success. username : {}", username);
        log.info("login success. accessToken : {}", accessToken);
        log.info("login success. expiration: {}", accessTokenExpiration);

        if (session != null){
            String redirectUrl = (String) session.getAttribute("prevPage");
            if (redirectUrl != null){
                session.removeAttribute("prevPage");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            }
            else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
        else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private String extractUsername(Authentication authentication){
        log.info("authentication.getPrincipal : {}", authentication.getPrincipal());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
