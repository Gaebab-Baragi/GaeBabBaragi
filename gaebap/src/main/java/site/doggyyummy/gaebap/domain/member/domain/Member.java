package site.doggyyummy.gaebap.domain.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
public class Member {

    @Id
    @Column
    private Long id;

    @Column (unique = true)
    private String name; //멤버 아이디에 해당합니다.

    @Column
    private String password;

    @Column (unique = true)
    private String nickname;

    @Column (unique = true)
    private String email;

    @Column
    private String authority;

    @Column(name = "register_date")
    private Timestamp registerDate;

    /* 나중에 추가하게 될 필드
       List<Recipe> recipes;
       List<Recipe> likes;
       List<Pet> pets;
     */
}
