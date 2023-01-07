package com.example.familytree.DTO;

import com.example.familytree.entity.Person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    private int id;
    private String name;

    public static PersonResponse fromPerson(Person person){
        return new PersonResponse(person.getId(), person.getName());
    }
}
