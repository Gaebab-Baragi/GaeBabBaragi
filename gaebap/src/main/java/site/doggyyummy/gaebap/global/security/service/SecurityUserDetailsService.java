package site.doggyyummy.gaebap.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.user.domain.SecurityUser;
import site.doggyyummy.gaebap.domain.user.domain.User;
import site.doggyyummy.gaebap.domain.user.repository.UserRepository;

import java.util.function.Supplier;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    //나중에 생성자 주입으로 바꿔야 함
    @Autowired
    private UserRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("user name not found");

        User user = userRepository.findUserByName(username).orElseThrow(s);

        return new SecurityUser(user);
    }
}
