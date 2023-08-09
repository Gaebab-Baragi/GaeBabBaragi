package site.doggyyummy.gaebap.domain.member.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.pet.entity.Pet;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "회원")
public class Member {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Schema(description = "유저 테이블의 pk")
    private Long id;

    @Column (unique = true, nullable = false)
    @Schema(description = "이메일")
    private String username;

    @Column (nullable = true)
    @Schema(description = "비밀번호")
    private String password;

    @Column (unique = true, nullable = false)
    @Schema(description = "닉네임")
    private String nickname;

    @Column (columnDefinition = "varchar(32) default 'GUEST'")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    @Schema(description = "프로필 이미지의 주소")
    private String profileUrl;

    @Column
    @Schema(description = "가입일")
    private Timestamp registerDate;

    @OneToMany(mappedBy = "member")
    @Schema(description = "회원이 작성한 레시피")
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Schema(description = "회원의 좋아요 목록")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Schema(description = "회원의 반려견")
    private List<Pet> pets = new ArrayList<>();


    @OneToMany(mappedBy = "host")
    @Schema(description = "회원이 호스팅할 화상 회의")
    private List<Meeting> hostedMeeting = new ArrayList<>();

    /**
     * 아래는 Security 및 OAuth2 관련한 필드
     */
    @Column(unique = true)
    @Schema(description = "DB에 저장된 리프레시 토큰")
    private String refreshToken;

    @Schema(description = "리프레시 토큰을 갱신")
    public void updateRefreshToken(String refreshToken){
       this.refreshToken = refreshToken;
    }

}
