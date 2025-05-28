package com.example.ticket_platform.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Trova un client tramite username (da User associato)
    Optional<Client> findByUserUsername(String username);

    // Trova tutti i clienti in base al loro tipo di abbonamento (membershipStatus)
    List<Client> findByMembershipStatus(String membershipStatus);

    // Trova un client tramite email
    Optional<Client> findByUserEmail(String email);
}
