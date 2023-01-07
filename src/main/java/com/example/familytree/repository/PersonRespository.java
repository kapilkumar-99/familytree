package com.example.familytree.repository;

import org.springframework.data.repository.CrudRepository;


import com.example.familytree.entity.Person;
import java.util.List;

public interface PersonRespository extends CrudRepository<Person, Integer>{

    List<Person> findByMaritalStatus(String martialStatus);

    List<Person> findByDateOfDeath(String dateOfDeath);

    List<Person> findByDateOfDeathLike(String string);
}
