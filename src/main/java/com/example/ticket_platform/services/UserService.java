package com.example.ticket_platform.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.Role;
import com.example.ticket_platform.models.User;
import com.example.ticket_platform.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Questo metodo verifica se un utente ha il ruolo di "ADMIN"
    public boolean isAdmin(Authentication authentication) {
        // Ottieni l'username dell'utente autenticato
        String username = authentication.getName();

        // Recupera l'utente dal database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Controlla se l'utente ha il ruolo di "ADMIN"
        for (Role role : user.getRoles()) {
            if (role.getName().equals("ADMIN")) {
                return true;  // L'utente ha il ruolo "ADMIN"
            }
        }
        return false;  // L'utente non ha il ruolo "ADMIN"
    }

    // Questo metodo verifica se un utente ha il ruolo di "OPERATOR"
    public boolean isOperator(Authentication authentication) {
        // Ottieni l'username dell'utente autenticato
        String username = authentication.getName();

        // Recupera l'utente dal database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Controlla se l'utente ha il ruolo di "OPERATOR"
        for (Role role : user.getRoles()) {
            if (role.getName().equals("OPERATOR")) {
                return true;  // L'utente ha il ruolo "OPERATOR"
            }
        }
        return false;  // L'utente non ha il ruolo "OPERATOR"
    }

     // Recupera lo stato di connessione di un singolo utente
    public boolean getUserOnlineStatus(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return userOpt.get().isIsOnline();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Recupera tutti gli utenti online o offline
    public List<User> getAllUsersByOnlineStatus(boolean isOnline) {
        return userRepository.findByIsOnline(isOnline);
    }

    // Recupera tutti gli utenti online o offline per un determinato ruolo
    public List<User> getAllUsersByOnlineStatusAndRole(boolean isOnline, String roleName) {
        return userRepository.findByIsOnlineAndRoles_Name(isOnline, roleName);
    }

    // Imposta lo stato online/offline per un singolo utente
    public void setUserOnlineStatus(Long userId, boolean isOnline) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setIsOnline(isOnline);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}


