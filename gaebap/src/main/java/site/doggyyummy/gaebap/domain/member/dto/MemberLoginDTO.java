package site.doggyyummy.gaebap.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginDTO {
    String username;
    String password;
}
