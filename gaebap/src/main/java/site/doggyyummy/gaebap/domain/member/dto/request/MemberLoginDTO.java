package site.doggyyummy.gaebap.domain.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberLoginDTO {
    String username;
    String password;
}
