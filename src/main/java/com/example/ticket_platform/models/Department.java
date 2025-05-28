package com.example.ticket_platform.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Department name cannot be blank")
    private String name;

    // Un dipartimento è gestito da un admin
    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;  // Relazione ManyToOne con Admin

    // Un dipartimento può avere più categorie
    @OneToMany(mappedBy = "department")
    private List<TicketCategory> categories;  // Relazione OneToMany con Category

    // Un dipartimento può avere più operatori
    @ManyToMany(mappedBy = "departments")
    private List<Operator> operators;  // Relazione ManyToMany con Operator

    // Getter and Setter
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

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<TicketCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<TicketCategory> categories) {
        this.categories = categories;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }
}


