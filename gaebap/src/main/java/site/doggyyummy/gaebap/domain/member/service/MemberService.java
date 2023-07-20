package site.doggyyummy.gaebap.domain.member.service;

import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.domain.Member;

import java.util.Optional;

public interface MemberService {

    void signUp(Member member);
    Optional<Member> findByName(String username);

}
