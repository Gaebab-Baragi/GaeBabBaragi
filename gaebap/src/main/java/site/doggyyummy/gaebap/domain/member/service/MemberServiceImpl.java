package site.doggyyummy.gaebap.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(Member member) throws Exception{
        validateMemberRegistration(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
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
    private void validateMemberRegistration(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        if (isValidRegistrationName(member.getName())) throw new Exception();
        if (isValidRegistrationNickname(member.getNickname())) throw new Exception();
        if (isValidRegistrationEmail(member.getEmail())) throw new Exception();
    }

    private void validateMemberModification(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        if (!isValidNicknameModification(member)) throw new Exception();
        if (!isValidEmailModification(member)) throw new Exception();
    }

    @Override
    public boolean isValidNicknameModification(Member member) {
        Member origin = findByName(member.getName()).get();
        if (origin == null) return false;
        if (!isValidNameFormat(member.getNickname())) return false;
        if (member.getNickname().equals(origin.getNickname())) return true;
        return !isDuplicateNickname(member.getNickname());
    }

    @Override
    public boolean isValidEmailModification(Member member) {
        Member origin = findByName(member.getName()).get();
        if (origin == null) return false;
        if (member.getEmail().equals(origin.getEmail())) return true;
        return !isDuplicateEmail(member.getEmail());
    }

    @Override
    public boolean isValidRegistrationName(String registerName){
        if (!isValidNameFormat(registerName)) return false;
        return !isDuplicateName(registerName);
    }

    @Override
    public boolean isValidRegistrationNickname(String nickname) {
        if (!isValidNicknameFormat(nickname)) return false;
        return !isDuplicateNickname(nickname);
    }

    @Override
    public boolean isValidRegistrationEmail(String email) {
        return !isDuplicateEmail(email);
    }
    private boolean isDuplicateName(String name){
        return memberRepository.existsMemberByName(name);
    }

    private boolean isDuplicateNickname(String nickname){
        return memberRepository.existsMemberByNickname(nickname);
    }

    private boolean isDuplicateEmail(String email){
        return memberRepository.existsMemberByEmail(email);
    }

    private boolean isValidNameFormat(String name){
        Integer length = name.length();
        if (length < 5 || length > 20) return false;
        String regex = "/^[a-z0-9]*$/";
        if (!name.matches(regex)) return false;
        return true;
    }
    private boolean isValidNicknameFormat(String nickname){
        Integer length = nickname.length();
        if (length == 0 || length > 10) return false;
        return true;
    }
}
