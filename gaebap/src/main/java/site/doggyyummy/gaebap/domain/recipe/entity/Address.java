package site.doggyyummy.gaebap.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Address {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String si;
    private String gu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Person person;

    @Builder
    public Address(String si,String gu,Person person){
        this.si=si;
        this.gu=gu;
        this.person=person;
    }

    public void addPerson(Person person){
        this.person=person;
        if (!person.getAddresses().contains(this)) {
            person.getAddresses().add(this);
        }
    }
}
