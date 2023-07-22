package site.doggyyummy.gaebap.domain.member.service;

import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.rmi.server.ExportException;
import java.util.Optional;

public interface MemberService {

    void signUp(Member member) throws Exception;
    void modify(Member member) throws Exception;
    Optional<Member> findByName(String username);
    boolean isDuplicateName(String name);
    boolean isDuplicateNickname(String nickname);
    boolean isDuplicateEmail(String email);
}
