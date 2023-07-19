package site.doggyyummy.gaebap.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User{

    @Id
    @Column
    private Long id;

    @Column
    private String name; //멤버 아이디에 해당합니다.

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
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
