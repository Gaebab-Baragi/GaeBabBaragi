package site.doggyyummy.gaebap.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public void signUp(Member member) throws Exception{
        validateMemberRegistration(member);
        memberRepository.save(member);
    }

    @Override
    public void modify(Member member) throws Exception{
        Member memberToModify = memberRepository.findMemberByName(member.getName()).orElseThrow(() -> new Exception());
        validateMemberModification(member);

        memberToModify.setNickname(member.getNickname());
        memberToModify.setEmail(member.getEmail());
        memberToModify.setPassword(member.getPassword());
    }

    @Override
    public Optional<Member> findByName(String username){
        return memberRepository.findMemberByName(username);
    }

    @Override
    public boolean isDuplicateName(String name){
        return memberRepository.existsMemberByName(name);
    }

    @Override
    public boolean isDuplicateNickname(String nickname){
        return memberRepository.existsMemberByNickname(nickname);
    }

    @Override
    public boolean isDuplicateEmail(String email){
        return memberRepository.existsMemberByEmail(email);
    }

    private void validateMemberRegistration(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        if (isDuplicateName(member.getName())) throw new Exception();
        if (isDuplicateNickname(member.getNickname())) throw new Exception();
        if (isDuplicateEmail(member.getEmail())) throw new Exception();
    }

    private void validateMemberModification(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        Member origin = findByName(member.getName()).orElseThrow(() -> new Exception());
        if (!origin.getNickname().equals(member.getNickname()) && isDuplicateNickname(member.getNickname())) throw new Exception();
        if (!origin.getEmail().equals(member.getEmail()) && isDuplicateEmail(member.getEmail())) throw new Exception();
    }
}
