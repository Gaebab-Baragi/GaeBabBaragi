package site.doggyyummy.gaebap.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByName(String username);

    boolean existsMemberByName(String name);
    boolean existsMemberByNickname(String name);
    boolean existsMemberByEmail(String email);
}
