package com.example.ticket_platform.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class TicketLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "L'azione non può essere vuota o contenere solo spazi.")
    @Column(nullable = false)
    private String action;

    // Stato del ticket durante il log (CREATED, IN_PROGRESS, COMPLETED, ecc.)
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    // Relazione ManyToOne con Ticket (Ogni TicketLog è associato a un Ticket)
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    // Relazione ManyToOne con User (Ogni TicketLog è creato da un Admin, Operator o Cliente)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
