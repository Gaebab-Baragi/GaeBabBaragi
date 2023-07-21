package site.doggyyummy.gaebap.domain.meeting.entity;

import jakarta.persistence.*;
import lombok.*;
import site.doggyyummy.gaebap.domain.member.entity.Member;
import site.doggyyummy.gaebap.domain.recipe.entity.Recipe;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meeting {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String password;

    private Integer limit;

    @Column(nullable = false)
    private String title;

    private String description;

    @OneToOne(mappedBy = "meeting")
    private Member host;

    private LocalDateTime startTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
