package com.example.ticket_platform.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.ticket_platform.models.Role;
import com.example.ticket_platform.models.User;


public class DatabaseUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final boolean enabled;

    public DatabaseUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = true;  // o puoi usare un campo del tuo User se esiste (es: user.isEnabled())
        this.authorities = new ArrayList<>();
        for (Role ruolo : user.getRoles()) {
            this.authorities.add(new SimpleGrantedAuthority(ruolo.getName()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // puoi aggiungere logica se hai un campo di scadenza account
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // puoi aggiungere logica se hai un campo di blocco account
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // puoi aggiungere logica se le credenziali hanno una scadenza
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;  // oppure puoi usare user.isEnabled() se lo hai nel tuo modello
    }

    public Long getId() {
        return id;
    }
}


/* public class DatabaseUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public DatabaseUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = new ArrayList<>();
        for (Role ruolo : user.getRoles()) {
            this.authorities.add(new SimpleGrantedAuthority(ruolo.getName()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Long getId() {
        return id;
    }
}
 */

