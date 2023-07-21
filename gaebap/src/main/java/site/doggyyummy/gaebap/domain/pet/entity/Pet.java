package site.doggyyummy.gaebap.domain.pet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Pet {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column
    private LocalDateTime birthDate;

    @Column
    private Double weight;

    @Column
    private String imgUrl;

    @Column
    @OneToMany(mappedBy = "pet")
    private List<Forbidden> forbiddens = new ArrayList<>();

}