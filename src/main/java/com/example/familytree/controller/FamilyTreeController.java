package com.example.familytree.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.familytree.DTO.AddParentRequest;
import com.example.familytree.DTO.GetPeopleResponse;
import com.example.familytree.DTO.PersonResponse;
import com.example.familytree.DTO.PersonsListResponse;
import com.example.familytree.entity.Parents;
import com.example.familytree.entity.Person;
import com.example.familytree.repository.ParentRepository;
import com.example.familytree.repository.PersonRespository;

@RestController
public class FamilyTreeController {

    @Autowired
    PersonRespository personRespository;

    @Autowired
    ParentRepository parentRepository;

    @PostMapping("/admin/person")
    public String addPerson(@RequestBody Person person){
        System.out.println(person.toString());
        personRespository.save(person);
        return "Person added Sucessfully";
    }

    @PutMapping("/admin/{id}")
    public String updatePerson(@PathVariable Integer id,@RequestParam String name, @RequestParam String dateOfBirth, @RequestParam String matiralStatus,
                               @RequestParam String dateOfDead){
        Optional<Person> person = personRespository.findById(id);
        if(person.isPresent()){
            person.get().setName(name);
            person.get().setDateOfBirth(dateOfBirth);
            person.get().setMaritalStatus(matiralStatus);
            person.get().setDateOfDeath(dateOfDead);
            personRespository.save(person.get());
        }

        return "Person sucessfully update";
    }
    
    @PostMapping("/admin/add/parent")
    public String addParent(@RequestBody AddParentRequest parentRequest){
        Optional<Person> parent = personRespository.findById(parentRequest.getParentId());
        Optional<Person> child = personRespository.findById(parentRequest.getChildId());
        if(parent.isPresent() && child.isPresent()){
            Parents parents = new Parents();
            parents.setParent(parent.get());
            parents.setChild(child.get());
            parentRepository.save(parents);
            return "Parent added sucessfully";
        }
        return "Sorry! Something is wrong with your data input";
    }


    @GetMapping("/admin/martialStatus")
    public List<GetPeopleResponse> findByMartialStatus(@RequestParam String martialStatus){
        List<Person> people = personRespository.findByMaritalStatus(martialStatus);
        List<GetPeopleResponse> marriedPeople = new ArrayList<>();
        for(Person person : people){
            GetPeopleResponse peopleResponse = new GetPeopleResponse(person.getId(),person.getName(), person.getCnic(), 
                                                        person.getDateOfBirth(),person.getDateOfDeath(), martialStatus);
                marriedPeople.add(peopleResponse);            
        }
        return marriedPeople;
    }

    @GetMapping("/admin/parents")
    public PersonsListResponse findParent(@RequestParam Integer childId) throws Exception{
        Optional<Person> person = personRespository.findById(childId);
        Person optionalPerson = person.orElseThrow(() -> new Exception("Sorry! This child is not found"));
       List<PersonResponse> people = new ArrayList<>();
       for(Parents parentalRelation : optionalPerson.getParents()){
            people.add(PersonResponse.fromPerson(parentalRelation.getParent()));
       }
       return new PersonsListResponse(people);
    }

    @GetMapping("/admin/children")
    public PersonsListResponse findChildren(@RequestParam Integer parentId) throws Exception{
        Optional<Person> person = personRespository.findById(parentId);
        Person optionalPerson = person.orElseThrow(() -> new Exception("Sorry! Parent is not found"));
        List<PersonResponse> children = new ArrayList<>();
       for(Parents parentalRealtionship : optionalPerson.getChildren()){
            children.add(PersonResponse.fromPerson(parentalRealtionship.getChild()));
       }
       return new PersonsListResponse(children);
    }

    @GetMapping("/admin/alive")
    public List<GetPeopleResponse> alive(){
        List<Person> people = personRespository.findByDateOfDeath("");
        List<GetPeopleResponse> alivepeople = new ArrayList<>();
        for(Person alive : people){
            GetPeopleResponse peopleResponse = new GetPeopleResponse(alive.getId(),alive.getName(), alive.getCnic(), alive.getDateOfBirth(),
            alive.getMaritalStatus());
            alivepeople.add(peopleResponse);
        }
        return alivepeople;
    }


    @GetMapping("/admin/dead")
    public List<GetPeopleResponse> dead(){
        String dateOfDeath = "-";
        List<Person> people = personRespository.findByDateOfDeathLike("%"+ dateOfDeath + "%");
        List<GetPeopleResponse> alivepeople = new ArrayList<>();
        for(Person died : people){
            GetPeopleResponse peopleResponse = new GetPeopleResponse(died.getId(),died.getName(), died.getCnic(), died.getDateOfBirth(),
            died.getDateOfDeath(), died.getMaritalStatus());
            alivepeople.add(peopleResponse);
        }
        return alivepeople;
    }


    @GetMapping("/admin/sibling")
    public PersonsListResponse sibling(@RequestParam Integer childId) throws Exception{
        Optional<Person> optionalPerson = personRespository.findById(childId); 
        Person person = optionalPerson.orElseThrow(()-> new Exception("Person is not found"));
        List<PersonResponse> people = new ArrayList<>();
        for(Parents parentalRelationship : person.getParents()){
            for(Parents siblings : parentalRelationship.getParent().getChildren()){
                if(siblings.getChild().getId() == childId){
                    continue;
                }
                people.add(PersonResponse.fromPerson(siblings.getChild()));
            }
        }
       
        return new PersonsListResponse(people);
    }

    @GetMapping("/admin/grandparent/{personId}")
    public PersonsListResponse grandParents(@PathVariable Integer personId) throws Exception{
        Optional<Person> optionalPerson = personRespository.findById(personId);
        Person person = optionalPerson.orElseThrow(()-> new Exception("Person not found"));
        List<PersonResponse> people = new ArrayList<>();
        for(Parents parentalRelationship : person.getParents()){
            for(Parents grandParentalRelatioship : parentalRelationship.getParent().getParents()){
                people.add(PersonResponse.fromPerson(grandParentalRelatioship.getParent()));
            }
        }
        return new PersonsListResponse(people);
    }
    
}
