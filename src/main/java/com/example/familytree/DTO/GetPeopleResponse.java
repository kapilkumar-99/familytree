package com.example.familytree.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPeopleResponse {
    private int id;
    private String name;
    private String cnic;
    private String dateOfBirth;
    private String dateOfDeath;
    private String maritalStatus;

    public GetPeopleResponse(int id, String name, String cnic, String dateOfBirth, String maritalStatus) {
        this.id = id;
        this.name = name;
        this.cnic = cnic;
        this.dateOfBirth = dateOfBirth;
        this.maritalStatus = maritalStatus;
    }
    
}
