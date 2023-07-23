package site.doggyyummy.gaebap.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import site.doggyyummy.gaebap.domain.like.entity.Bookmark;
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

}
