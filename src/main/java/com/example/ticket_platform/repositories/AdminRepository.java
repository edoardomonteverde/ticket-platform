package com.example.ticket_platform.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    

    // Trova tutti gli amministratori che sono Super Admin
    List<Admin> findByIsSuperAdminTrue();

    // Trova un amministratore in base all'utente associato (User)
    Optional<Admin> findByUserUsername(String username);

    // Trova un amministratore in base all'ID dell'utente
    Optional<Admin> findByUserId(Long userId);

    // Trova un amministratore in base alla sua area o specializzazione
    List<Admin> findByDepartment(String department);

    // Trova un amministratore in base all'area e se è un super admin
    List<Admin> findByDepartmentAndIsSuperAdminTrue(String department);

}

