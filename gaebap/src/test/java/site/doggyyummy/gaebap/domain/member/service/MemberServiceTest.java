package site.doggyyummy.gaebap.domain.member.service;

import jakarta.transaction.Transactional;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.doggyyummy.gaebap.domain.member.dto.MemberModifyDTO;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.member.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 가입 테스트")
    void signUp() throws Exception {
        Member member1= new Member();
        member1.setName("member1");
        member1.setNickname("nick1");
        member1.setPassword("pass");
        member1.setEmail("member1@doggyyummy.site");

        memberService.signUp(member1);

        //이메일 중복

        Member member2= new Member();
        member2.setName("member2");
        member2.setNickname("nick2");
        member2.setPassword("pass");
        member2.setEmail("member1@doggyyummy.site");

        Assertions.assertThrows(Exception.class, () -> memberService.signUp(member2));

        //닉네임 중복
        member2.setNickname("nick1");
        member2.setEmail("member2@doggyyummy.site");
        Assertions.assertThrows(Exception.class, () -> memberService.signUp(member2));

        //아이디 중복
        member2.setNickname("nick2");
        member2.setName("member1");
        Assertions.assertThrows(Exception.class, () -> memberService.signUp(member2));

        //가입
        member2.setName("member2");
        memberService.signUp(member2);
    }

    @Test
    @DisplayName("회원 정보 수정")
    void modify() throws Exception{
        signUp();
        String modifyName = "member1";
        String password = "pass";
        String nickname = "modnick";
        String email = "member1@doggyyummy.site";//이메일은 바꾸지 않음

        System.out.println(memberService.findByName(modifyName).get().getNickname());

        Member modifyMemberEntity = MemberModifyDTO.toEntity(new MemberModifyDTO(modifyName, password, nickname, email));

        memberService.modify(modifyMemberEntity);
        modifyMemberEntity.setNickname("nick2");
        System.out.println(memberService.findByName(modifyName).get().getNickname());

        //중복된 닉네임
        Assertions.assertThrows(Exception.class, () -> memberService.modify(modifyMemberEntity));

    }

    @Test
    @DisplayName("있는 회원 둘, 없는 회원 하나")
    void findByName() throws Exception {
        signUp();

        System.out.println(memberService.findByName("member1").get().getName());
        System.out.println(memberService.findByName("member2").get().getName());

        Assertions.assertThrows(Exception.class, () -> memberService.findByName("member3")
                                                        .orElseThrow(() -> new Exception("그런 사람 없습니다")));
    }

}