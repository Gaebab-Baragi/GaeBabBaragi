package site.doggyyummy.gaebap.global.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.global.security.domain.SecurityUser;
import site.doggyyummy.gaebap.domain.member.domain.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import java.util.function.Supplier;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    //나중에 생성자 주입으로 바꿔야 함
    @Autowired
    private MemberRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("user name not found");

        Member member = userRepository.findUserByName(username).orElseThrow(s);

        return new SecurityUser(member);
    }
}
