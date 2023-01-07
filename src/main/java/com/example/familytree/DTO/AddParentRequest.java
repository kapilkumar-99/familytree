package com.example.familytree.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddParentRequest {
    // @JsonProperty("parent_id")
    private int parentId;
    // @JsonProperty("child_id")
    private int childId;
}
