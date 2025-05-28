package com.example.ticket_platform.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

    // Trova un operatore in base all'username dell'utente associato
    Optional<Operator> findByUserUsername(String username);

    // Trova tutti gli operatori con uno specifico stato di attività
    List<Operator> findByActivityStatus(boolean activityStatus);

    // Trova operatori in base alla specializzazione
    List<Operator> findBySpecialization(String specialization);

    // Trova operatori in base al dipartimento
    List<Operator> findByDepartment(String department);

    // Trova operatori in base allo stato di attività e dipartimento
    List<Operator> findByActivityStatusAndDepartment(boolean activityStatus, String department);
}
