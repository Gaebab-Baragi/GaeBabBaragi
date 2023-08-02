package site.doggyyummy.gaebap.domain.member.service;

import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {

    void signUp(Member member) throws Exception;
    void modify(Member member, String file, String fileType) throws Exception;
    Optional<Member> findByName(String username);
    void validateNicknameModification(Member member) throws Exception;

    void validateRegistrationUsername(String registerUsername) throws Exception;
    void validateRegistrationNickname(String email) throws Exception;
    void uploadImageByUrl(Member member) throws Exception;
}
