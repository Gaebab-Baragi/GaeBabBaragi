package site.doggyyummy.gaebap.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.domain.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    @Override
    public void signUp(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByName(String username){
        return memberRepository.findMemberByName(username);
    }
}
