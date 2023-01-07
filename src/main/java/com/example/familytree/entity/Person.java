package com.example.familytree.entity;


import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String cnic;
    private String dateOfBirth;
    private String dateOfDeath;
    private String maritalStatus;

    @OneToMany(mappedBy = "child")
    private List<Parents> parents;

    @OneToMany(mappedBy = "parent")
    private List<Parents> children;
}
