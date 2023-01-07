package com.example.familytree.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.familytree.entity.Parents;

public interface ParentRepository extends CrudRepository<Parents, Integer>{

    List<Parents> findByChildId(Integer childId);

    List<Parents> findByParentId(Integer parentId);

    Optional<Parents> findFirstByChildId(Integer childId);

    // List<GetParentResponse> findByParentId(List<Parents> person);

    // List<Parents> findByGrandParentId(List<Parents> parent);
}
