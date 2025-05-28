package com.example.ticket_platform.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Metodo per trovare un utente tramite username
    Optional<User> findByUsername(String username);

    // Metodo per trovare un utente tramite email
    Optional<User> findByEmail(String email);

    // Trova un utente in base allo stato online (online/offline)
    List<User> findByIsOnline(boolean isOnline);

    // Trova gli utenti in base allo stato di connessione e ruolo (ad esempio, solo operatori online)
    List<User> findByIsOnlineAndRoles_Name(boolean isOnline, String roleName);
}

