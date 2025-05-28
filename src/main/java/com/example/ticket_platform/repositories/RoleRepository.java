package com.example.ticket_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Metodo per trovare i ruoli tramite nome
    List<Role> findByName(String name);
}
