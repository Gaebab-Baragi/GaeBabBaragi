package site.doggyyummy.gaebap.domain.pet.entity;

import jakarta.persistence.*;
import lombok.*;
import site.doggyyummy.gaebap.domain.recipe.entity.Ingredient;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Forbidden {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PET_ID")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INGREDIENT_ID")
    private Ingredient ingredient;
}