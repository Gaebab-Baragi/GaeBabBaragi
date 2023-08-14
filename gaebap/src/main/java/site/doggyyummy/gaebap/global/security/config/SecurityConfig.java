package site.doggyyummy.gaebap.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.UriComponentsBuilder;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.global.security.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import site.doggyyummy.gaebap.global.security.filter.JwtAuthenticationFilter;
import site.doggyyummy.gaebap.global.security.handler.LoginFailureHandler;
import site.doggyyummy.gaebap.global.security.handler.LoginSuccessHandler;
import site.doggyyummy.gaebap.global.security.handler.oauth2.OAuth2LoginFailureHandler;
import site.doggyyummy.gaebap.global.security.handler.oauth2.OAuth2LoginSuccessHandler;
import site.doggyyummy.gaebap.global.security.service.JwtService;
import site.doggyyummy.gaebap.global.security.service.PrincipalDetailsService;
import site.doggyyummy.gaebap.global.security.service.oauth2.CustomOAuth2UserService;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Value("${current.back}")
    private String backUrl;

    @Value("${current.front}")
    private String frontUrl;



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin(frontUrl);
        config.addAllowedOrigin("http://localhost:5443");
        config.addAllowedOrigin("https://doggy-yummy:5443");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain httpFilterChain (HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors((cors) ->
                        cors.configurationSource(corsConfigurationSource()))
                .csrf((csrf) ->
                        csrf.disable())
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .requiresChannel((requiresChannel) -> requiresChannel
                        .requestMatchers("/api/oauth2/authorization").requiresSecure()
                )
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/api/member/register/**").permitAll()
                        .requestMatchers("/api/checkLogin").permitAll()
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/recipes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/recipes/recipestitle").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/recipes/writer/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/recipes/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/recipes/searchlike").permitAll()
                        .requestMatchers("/api/ingredients").permitAll()
                        .requestMatchers("/api/recipes/popular").permitAll()
                        .requestMatchers("/api//searchlike").permitAll()
                        .requestMatchers("/api/pets/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/meetings").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/meetings/{id}").permitAll()
                        .requestMatchers("/forbiddens").permitAll()
                        .requestMatchers("/participants/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login((oauth2login) ->
                    oauth2login
                            .loginPage(frontUrl+"/login")
                            .successHandler(oAuth2LoginSuccessHandler)
                            .failureHandler(oAuth2LoginFailureHandler)
                            .authorizationEndpoint((endpoint) -> endpoint
                                    .baseUri("/api/oauth2/authorization"))
                            .redirectionEndpoint((endpoint) ->
                                    endpoint.baseUri("/api/login/oauth2/code/*"))
                            .userInfoEndpoint((endpoint) ->
                                    endpoint.userService(customOAuth2UserService))
                )
                .logout((logout) ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    return;
                                })
                                .invalidateHttpSession(true)
                                .deleteCookies("refreshToken")
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                                log.info("response header : {}", response.getHeader("Access-Control-Allow-Origin"));
                                response.setStatus(401);
                            }
                        }))
        ;

        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        return delegatingPasswordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new PrincipalDetailsService(memberRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler(jwtService, memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    //아래 두 필터는 Bean으로 설정 X
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() throws Exception{
        CustomJsonUsernamePasswordAuthenticationFilter namePasswordFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(new ObjectMapper());

        namePasswordFilter.setAuthenticationManager(authenticationManager());
        namePasswordFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        namePasswordFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return namePasswordFilter;
    }

    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        JwtAuthenticationFilter jwtAuthenticationFilter
                = new JwtAuthenticationFilter(jwtService, memberRepository);
        return jwtAuthenticationFilter;
    }

}
