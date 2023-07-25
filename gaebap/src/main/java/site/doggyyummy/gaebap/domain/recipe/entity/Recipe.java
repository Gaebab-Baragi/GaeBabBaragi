package site.doggyyummy.gaebap.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.like.entity.Bookmark;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Long hit=0L;

    // 보류
    private String imageUrl;

    // 보류
    private String videoUrl;

    @OneToMany(mappedBy = "recipe", cascade=CascadeType.REMOVE)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade=CascadeType.REMOVE)
    private List<Step> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe")
    private List<Meeting> meetings = new ArrayList<>();

    @OneToMany(mappedBy = "recipe")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "recipe")
    private List<Bookmark> bookmarks = new ArrayList<>();

    public void setMember(Member member){
        this.member=member;
        member.getRecipes().add(this);
    }

    public void setSteps(List<Step> steps){
        this.steps=steps;
        for (Step step : steps) {
            step.setRecipe(this);
        }
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients){
        this.recipeIngredients=recipeIngredients;
        for(RecipeIngredient r:recipeIngredients){
            r.setRecipeIngredient(this,r.getIngredient());
        }
    }


}