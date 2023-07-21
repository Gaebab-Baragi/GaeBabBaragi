package site.doggyyummy.gaebap.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.entity.Member;
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

    @Override
    public void validateName(String name) throws Exception{
        try {
            validateNameFormat(name);
            validateDuplicateName(name);
        }
        catch (Exception e){



        }
    }

    @Override
    public void validateDuplicateName(String name) throws Exception {//Exception의 종류는 후에 바꿈
        if (memberRepository.existsMemberByName(name)) {
            throw new Exception();
        }
    }

    @Override
    public void validateNameFormat(String name) throws Exception {
        String usernameRegex= "^[a-zA-Z0-9]{8,20}$";
        if (name.matches(usernameRegex)) return;
        throw new Exception();
    }

    @Override
    public void validateNickname(String nickname) {

    }

    @Override
    public void validateEmail(String email) {

    }
}
