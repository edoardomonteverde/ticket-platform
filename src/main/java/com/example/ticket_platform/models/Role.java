package com.example.ticket_platform.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il ruolo non può essere vuoto.")
    @Size(min = 3, max = 30, message = "Il ruolo deve essere lungo tra 3 e 30 caratteri.")
    @Column(nullable = false, unique = true)
    private String name;  // Nome del ruolo (Admin, Operator, Client)

    // Relazione ManyToMany con User
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    // Getter e Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    
}
