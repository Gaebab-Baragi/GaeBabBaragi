package site.doggyyummy.gaebap.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doggyyummy.gaebap.domain.member.dto.MemberRegisterDTO;
import site.doggyyummy.gaebap.domain.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    @PostMapping("/register")
    public String signUp(@RequestBody MemberRegisterDTO registerDTO){
        try {
            memberService.signUp(MemberRegisterDTO.toEntity(registerDTO));
            return "reg";
        }
        catch (Exception e){
            return "exception";
        }
    }

    /** TODO
     *  회원가입
     *      아이디 중복 확인
     *      닉네임 중복 확인 중
     *          복 여부만 담아서 줄 것. 이외의 정보는 필요없음.
     *------------------------------------------------------
     *      이메일 인증 구현:
     *     |    이메일 형식 확인 -> 이건 프론트에서 처리
     *     |    이미 db에 등록된 이메일인지 확인 -> (이메일 중복 확인)
     *     |    인즘 메일 발송
     *     V    인증 확인
     *      회원관리 완료 -> db에 insert할 때 다시 체크됨
     *------------------------------------------------------
     *  로그인
     *      로그인 => 이건 좀 공부해서 만들어야 됨
     *
     */
}
