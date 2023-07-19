package site.doggyyummy.gaebap.domain.member.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberRegisterDTO {
    private String registerName;
    private String password;
    private String nickname;
    private String email;
}
