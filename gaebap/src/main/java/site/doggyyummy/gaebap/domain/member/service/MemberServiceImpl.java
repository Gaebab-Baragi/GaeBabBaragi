package site.doggyyummy.gaebap.domain.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.domain.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository memberRepository;

    @Override
    public void signUp(Member member) {
        Member regmember = memberRepository.save(member);
        System.out.println(regmember);
    }

    @Override
    public Optional<Member> findByName(String username){
        return memberRepository.findMemberByName(username);
    }
}
