package site.doggyyummy.gaebap.global.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.global.security.domain.SecurityUser;
import site.doggyyummy.gaebap.domain.member.domain.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private MemberRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("user name not found");

        Member member = userRepository.findMemberByName(username).orElseThrow(s);

        return new SecurityUser(member);
    }
}
