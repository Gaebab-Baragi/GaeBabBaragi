package site.doggyyummy.gaebap.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class RecipeIngredient {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INGREDIENT_ID")
    private Ingredient ingredient;

    private String amount;

    public void setRecipeIngredientIngredient(Ingredient ingredient){
        this.ingredient=ingredient;
    }
    public void setRecipeIngredient(Recipe recipe,Ingredient ingredient){
        this.recipe=recipe;
        this.ingredient=ingredient;
    }
}