package site.doggyyummy.gaebap.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.exception.custom.*;
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
        Member memberToModify = memberRepository.findByName(member.getUsername()).orElseThrow(() -> new Exception());
        validateMemberModification(member);

        memberToModify.setNickname(member.getNickname());
        memberToModify.setEmail(member.getEmail());
        memberToModify.setPassword(member.getPassword());
    }

    @Override
    public Optional<Member> findByName(String username) {
        return memberRepository.findByName(username);
    }

    @Override
    public void validateNicknameModification(Member member) throws Exception{
        Member origin = findByName(member.getUsername()).orElseThrow(()->{throw new NoSuchUserException();});
        if (!isValidNameFormat(member.getNickname())) throw new InvalidNicknameFormatException();
        if (member.getNickname().equals(origin.getNickname())) return;
        if (isDuplicateNickname(member.getNickname())) throw new DuplicateNicknameException();
    }

    @Override
    public void validateEmailModification(Member member)  throws NoSuchUserException, DuplicateEmailException{
        Member origin = findByName(member.getUsername()).orElseThrow(() -> {throw new NoSuchUserException();});
        if (member.getEmail().equals(origin.getEmail())) return;
        if (isDuplicateEmail(member.getEmail())) throw new DuplicateEmailException();
    }

    @Override
    public void validateRegistrationUsername(String registerName) throws InvalidNameFormatException, DuplicateUsernameException{
        if (!isValidNameFormat(registerName)) throw new InvalidNameFormatException();
        if (isDuplicateName(registerName)) throw new DuplicateUsernameException();
    }

    @Override
    public void validateRegistrationNickname(String nickname) throws InvalidNameFormatException, DuplicateNicknameException {
        if (!isValidNicknameFormat(nickname)) throw new InvalidNicknameFormatException();
        if (isDuplicateNickname(nickname)) throw new DuplicateNicknameException();
    }

    @Override
    public void validateRegistrationEmail(String email) throws DuplicateEmailException{
        if (isDuplicateEmail(email)) throw new DuplicateEmailException();
    }
    //=============================================================================================
    private boolean isDuplicateName(String name){
        return memberRepository.existsByName(name);
    }

    private boolean isDuplicateNickname(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    private boolean isDuplicateEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    private boolean isValidNameFormat(String name) {
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

    private void validateMemberRegistration(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        validateRegistrationUsername(member.getUsername());
        validateRegistrationNickname(member.getNickname());
        validateRegistrationEmail(member.getEmail());
    }

    private void validateMemberModification(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        validateNicknameModification(member);
        validateEmailModification(member);
    }
}
