package site.doggyyummy.gaebap.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${current.back}")
    private String backUrl;

    @Value("${current.front}")
    private String frontUrl;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring().requestMatchers("/api/member/register/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList(frontUrl));
        config.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
        config.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain httpFilterChain (HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors((cors) ->
                        cors.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/api/member/modify/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login((oauth2login) ->
                    oauth2login
                            .successHandler(oAuth2LoginSuccessHandler)
                            .failureUrl(frontUrl+"/login")
                            .failureHandler(oAuth2LoginFailureHandler)
                            .redirectionEndpoint((endpoint) ->
                                    endpoint.baseUri("/api/login/oauth2/code/*"))
                            .userInfoEndpoint((endpoint) ->
                                    endpoint.userService(customOAuth2UserService))
                )
                .logout((logout) ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                                .logoutSuccessUrl(frontUrl)
                                .invalidateHttpSession(true)
                );

        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
