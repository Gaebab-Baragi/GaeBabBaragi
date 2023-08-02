package site.doggyyummy.gaebap.domain.member.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.sql.Timestamp;

@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class MemberModifyDTO {

    private String username;
    private String nickname;
    private String file;
    private String fileType;

    public static Member toEntity(MemberModifyDTO modifyDTO){
        Member member = Member
                .builder()
                .username(modifyDTO.getUsername())
                .nickname(modifyDTO.getNickname())
                .build();
        return member;
    }
}
