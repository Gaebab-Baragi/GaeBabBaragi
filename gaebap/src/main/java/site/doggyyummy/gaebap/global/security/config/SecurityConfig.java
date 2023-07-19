package site.doggyyummy.gaebap.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        //TODO : userDetailService는 추후 구현해서 바꿀 것
        var userDetailsService = new InMemoryUserDetailsManager();

        //TODO : 이후 삭제
        var user = User.withUsername("john")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();

        userDetailsService.createUser(user);

        return userDetailsService;
    }




}
