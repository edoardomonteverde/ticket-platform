package com.example.ticket_platform.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Trova un dipartimento in base al suo nome
    List<Department> findByName(String name);

}
