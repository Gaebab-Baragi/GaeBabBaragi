package site.doggyyummy.gaebap.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.exception.custom.*;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
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
        Member memberToModify = memberRepository.findByUsername(member.getUsername()).orElseThrow(() -> new Exception());
        validateMemberModification(member);

        memberToModify.setNickname(member.getNickname());
        memberToModify.setPassword(member.getPassword());
    }

    @Override
    public Optional<Member> findByName(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public void validateNicknameModification(Member member) throws Exception{
        Member origin = findByName(member.getUsername()).orElseThrow(()->{throw new NoSuchUserException();});
        if (!isValidNicknameFormat(member.getNickname())) throw new InvalidNicknameFormatException();
        if (member.getNickname().equals(origin.getNickname())) return;
        if (isDuplicateNickname(member.getNickname())) throw new DuplicateNicknameException();
    }


    @Override
    public void validateRegistrationUsername(String registerName) throws InvalidNameFormatException, DuplicateUsernameException{
        if (isDuplicateName(registerName)) throw new DuplicateEmailException();
    }

    @Override
    public void validateRegistrationNickname(String nickname) throws InvalidNameFormatException, DuplicateNicknameException {
        if (!isValidNicknameFormat(nickname)) throw new InvalidNicknameFormatException();
        if (isDuplicateNickname(nickname)) throw new DuplicateNicknameException();
    }

    //=============================================================================================
    private boolean isDuplicateName(String name){
        return memberRepository.existsByUsername(name);
    }

    private boolean isDuplicateNickname(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    private boolean isValidNicknameFormat(String nickname){
        Integer length = nickname.length();
        log.info("nickname : {}", nickname);
        if (length == 0 || length > 10) return false;
        return true;
    }

    private void validateMemberRegistration(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        validateRegistrationUsername(member.getUsername());
        validateRegistrationNickname(member.getNickname());
    }

    private void validateMemberModification(Member member) throws Exception{ //TODO Exception마다 다른 걸로 상속하게 바꿀 것
        validateNicknameModification(member);
    }


    //TODO : 비밀번호 검증하는 부분이 있어야 함
}
