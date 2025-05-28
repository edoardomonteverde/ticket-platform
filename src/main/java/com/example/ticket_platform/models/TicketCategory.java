package com.example.ticket_platform.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class TicketCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome della categoria non può essere vuoto.")
    @Column(nullable = false)
    private String name;

    // Relazione ManyToMany con Ticket (Una categoria può appartenere a più Ticket, un Ticket può appartenere a più categorie)
    @ManyToMany(mappedBy = "ticketCategories")
    private List<Ticket> tickets; 

    // Relazione ManyToOne con Dipartimento
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
}

