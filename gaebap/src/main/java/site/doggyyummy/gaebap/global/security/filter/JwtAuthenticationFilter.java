package site.doggyyummy.gaebap.global.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.global.security.entity.PrincipalDetails;
import site.doggyyummy.gaebap.global.security.service.JwtService;
import site.doggyyummy.gaebap.global.security.util.PasswordUtil;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String LOGIN_URL = "/api/login";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("requestURI : {}", request.getRequestURI());
        if (request.getRequestURI().equals(LOGIN_URL)) {//일반
            filterChain.doFilter(request, response);
            return;
        }
        checkAccessTokenAndAuthentication(request, response, filterChain);
    }


    public void checkRefreshTokenAndReIssueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        log.info("checkRefreshTokenAndReIssueAccessToken() 호출");

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (refreshToken == null) return;

        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member-> {
                    String reIssuedRefreshToken = jwtService.createRefreshToken();
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(member.getUsername()),
                            reIssuedRefreshToken);
                    jwtService.updateRefreshToken(member.getUsername(), reIssuedRefreshToken);
                    saveAuthentication(member);
                    log.info("authenticatedMember : {}", member);
                });
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");

        Optional<String> accessToken = jwtService.extractAccessToken(request);
        log.info("accessToken : {}" , accessToken.orElseGet(() -> "no access token"));
        if (accessToken.isPresent() && jwtService.isTokenValid(accessToken.get())){
            Optional<String> name = jwtService.extractName(accessToken.get());
            if (name.isPresent()){
                Optional<Member> member = memberRepository.findByUsername(name.get());
                if (member.isPresent()) {
                    saveAuthentication(member.get());

                    String refreshToken = jwtService.extractRefreshToken(request)
                            .filter(jwtService::isTokenValid)
                            .orElse(null);

                    if (refreshToken == null) {
                        String reissuedRefreshToken = jwtService.createRefreshToken();
                        jwtService.updateRefreshToken(member.get().getUsername(), reissuedRefreshToken);
                        jwtService.sendAccessAndRefreshToken(response, accessToken.get(), reissuedRefreshToken);
                    }
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        checkRefreshTokenAndReIssueAccessToken(request, response);
        filterChain.doFilter(request, response);
    }


    public void saveAuthentication(Member member) {
        log.info("saveAuthentication() 호출");
        String password = member.getPassword();
        if (password == null) {
            password = PasswordUtil.generateRandomPassword();
            member.setPassword(password);
        }

        PrincipalDetails principalDetails = new PrincipalDetails(member);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails, null,
                        authoritiesMapper.mapAuthorities(principalDetails.getAuthorities()));

        log.info("authentication : {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}