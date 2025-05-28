package com.example.ticket_platform.services;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    // Verifica se l'utente autenticato ha il ruolo di ADMIN
    public boolean isAdmin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return true; // L'utente ha il ruolo "ADMIN"
            }
        }
        
        return false; // L'utente non ha il ruolo "ADMIN"
    }

    // Verifica se l'utente autenticato ha il ruolo di ADMIN
    public boolean isOperator(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_OPERATOR")) {
                return true; // L'utente ha il ruolo "OPERATOR"
            }
        }
        
        return false; // L'utente non ha il ruolo "OPERATOR"
    }
}
