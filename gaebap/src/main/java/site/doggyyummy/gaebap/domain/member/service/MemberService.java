package site.doggyyummy.gaebap.domain.member.service;

import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {

    void signUp(Member member);
    Optional<Member> findByName(String username);

    void validateName(String name) throws Exception;
    void validateDuplicateName(String name) throws Exception;
    void validateNameFormat(String name) throws Exception;
    void validateNickname(String nickname);
    void validateEmail(String email);
}
