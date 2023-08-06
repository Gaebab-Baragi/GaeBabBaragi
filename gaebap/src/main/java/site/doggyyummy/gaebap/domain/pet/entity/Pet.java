package site.doggyyummy.gaebap.domain.pet.entity;

import jakarta.persistence.*;
import lombok.*;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Pet {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String name;

    @Column(nullable=true)
    private String imgUrl;

    @OneToMany(mappedBy = "pet",cascade = CascadeType.ALL)
    private List<Forbidden> forbiddens = new ArrayList<>();

}