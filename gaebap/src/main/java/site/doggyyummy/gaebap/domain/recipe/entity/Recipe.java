package site.doggyyummy.gaebap.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.*;
import site.doggyyummy.gaebap.domain.comment.entity.Comment;
import site.doggyyummy.gaebap.domain.bookmark.entity.Bookmark;
import site.doggyyummy.gaebap.domain.meeting.entity.Meeting;
import site.doggyyummy.gaebap.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Recipe {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private Long hit=0L;

    private LocalDateTime nowTime ;

    private String imageUrl;
    private String imageKey;

    private String videoUrl;
    private String videoKey;

    @OneToMany(mappedBy = "recipe", cascade=CascadeType.REMOVE)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade=CascadeType.REMOVE)
    private List<Step> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.REMOVE)
    private List<Meeting> meetings = new ArrayList<>();

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "recipe",cascade=CascadeType.REMOVE)
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