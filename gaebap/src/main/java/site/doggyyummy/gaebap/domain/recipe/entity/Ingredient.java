package site.doggyyummy.gaebap.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.pet.entity.Forbidden;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ingredient {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private boolean edible;

    @Column
    @OneToMany(mappedBy = "ingredient")
    private List<Forbidden> forbiddens = new ArrayList<>();
}