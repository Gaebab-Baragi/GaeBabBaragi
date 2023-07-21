package site.doggyyummy.gaebap.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @Column
    @OneToMany(mappedBy = "member")
    private List<Recipe> recipes = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "member")
    private List<Recipe> likes = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "member")
    private List<Pet> pets;

    @Column
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    // 내가 생성한 미팅이라는걸 바로 알 수 있는 변수명으로 바꾸는건 어떻게 생각하시는지..
    private Meeting meeting;
}
