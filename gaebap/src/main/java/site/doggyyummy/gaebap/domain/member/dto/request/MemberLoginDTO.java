package site.doggyyummy.gaebap.domain.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginDTO {
    String username;
    String password;
}
