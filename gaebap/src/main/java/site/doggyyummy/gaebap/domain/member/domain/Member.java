package site.doggyyummy.gaebap.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", authority='" + authority + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
