package site.doggyyummy.gaebap.domain.member.service;

import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.rmi.server.ExportException;
import java.util.Optional;

public interface MemberService {

    void signUp(Member member) throws Exception;
    void modify(Member member) throws Exception;
    Optional<Member> findByName(String username);

    boolean isValidNicknameModification(Member member) throws Exception;
    boolean isValidEmailModification(Member member) throws  Exception;

    boolean isValidRegistrationName(String registerName);
    boolean isValidRegistrationNickname(String email);

    boolean isValidRegistrationEmail(String nickname);
}
