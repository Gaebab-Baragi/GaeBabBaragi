package site.doggyyummy.gaebap.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;
import site.doggyyummy.gaebap.global.security.entity.oauth2.CustomOAuth2User;
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/member/register/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
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
                        .requestMatchers("/member/modify/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login((oauth2login) ->
                    oauth2login
                            .successHandler(oAuth2LoginSuccessHandler)
                            .failureHandler(oAuth2LoginFailureHandler)
                            .userInfoEndpoint((endpoint) ->
                                    endpoint.userService(customOAuth2UserService))
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
