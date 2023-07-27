package site.doggyyummy.gaebap.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
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
public class Member {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, nullable = false)
    private String username; //멤버 아이디에 해당합니다.

    @Column
    private String password;

    @Column (unique = true, nullable = false)
    private String nickname;

    @Column
    private String authority;

    @Column
    private String profileUrl;

    @Column
    private Timestamp registerDate;

    @OneToMany(mappedBy = "member")
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Pet> pets = new ArrayList<>();

    @OneToOne(mappedBy = "host")
    private Meeting hostedMeeting;

    /**
     * 아래는 Security 및 OAuth2 관련한 필드
     */
    @Column
    private String refreshToken;

    @Column (unique = true)
    private String googleAccount;

    @Column (unique = true)
    private String kakaoAccount;

    @Column (unique = true)
    private String naverAccount;

    public void updateRefreshToken(String refreshToken){
       this.refreshToken = refreshToken;
    }

}
