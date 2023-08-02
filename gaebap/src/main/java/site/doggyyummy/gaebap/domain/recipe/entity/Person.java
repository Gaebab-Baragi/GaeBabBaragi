package site.doggyyummy.gaebap.domain.recipe.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Person {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    @OneToMany(mappedBy = "person")
    private List<Address> addresses=new ArrayList<>();

    @Builder
    public Person(String name,int age,List<Address> addresses){
        this.name=name;
        this.age=age;
        this.addresses=addresses;
    }

    public void addAddress(List<Address> addresses){
        this.addresses=addresses;
        for(Address a:addresses){
            a.addPerson(this);
        }
    }

}
