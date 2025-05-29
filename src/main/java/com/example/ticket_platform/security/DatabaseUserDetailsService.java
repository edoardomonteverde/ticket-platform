package com.example.ticket_platform.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.User;
import com.example.ticket_platform.repositories.UserRepository;


@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new DatabaseUserDetails(user);
    }
}


    /* @Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Iniezione delle dipendenze tramite costruttore
   
    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Metodo per caricare l'utente dal database in base al nome utente
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsername(username);

        if (optUser.isPresent()) {
            return new DatabaseUserDetails(optUser.get());  // Restituisce i dettagli dell'utente
        } else {
            throw new UsernameNotFoundException("Username not found: " + username);  // Se l'utente non esiste
        }
    }
} */