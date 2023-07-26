package site.doggyyummy.gaebap.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String username);
    Optional<Member> findByRefreshToken(String refreshToken);
    Optional<Member> findByEmail(String email);
    boolean existsByName(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
