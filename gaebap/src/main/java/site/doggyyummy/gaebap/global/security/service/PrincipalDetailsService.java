package site.doggyyummy.gaebap.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.global.security.entity.PrincipalDetails;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("username not found");

        log.info("loadUserByUsername : {}", username);
        Member member = memberRepository.findMemberByName(username).orElseThrow(s);

        log.info("locked? : {}", new PrincipalDetails(member).isAccountNonLocked());
        return new PrincipalDetails(member);
    }
}
