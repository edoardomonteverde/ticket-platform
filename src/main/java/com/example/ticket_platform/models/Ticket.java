package com.example.ticket_platform.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il titolo del ticket non può essere vuoto o contenere solo spazi.")
    @Size(min = 3, message = "Il titolo deve avere almeno 3 caratteri.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "La descrizione non può essere vuota o contenere solo spazi.")
    @Size(min = 10, message = "La descrizione deve avere almeno 10 caratteri.")
    @Column(nullable = false)
    private String description;

    @NotBlank(message = "Lo stato non può essere vuoto o contenere solo spazi.")
    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime closedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;

   // Relazione ManyToOne con User (Admin o Client) - L'utente che ha creato il ticket
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;  // Relazione ManyToOne con User (Admin o Client)

    // Relazione ManyToOne con User (Admin o Operator) per la gestione del ticket
    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = true)
    private User assignedTo;  //

    // Relazione OneToMany con TicketLog (Un ticket può avere molti log)
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketLog> ticketLogs;  //

    // Relazione OneToMany con Note (Un ticket può avere molte note)
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Note> notes;  //

    // Relazione ManyToMany con Category (Un ticket può appartenere a più categorie)
    @ManyToMany
    @JoinTable(
        name = "ticket_ticketcategory", 
        joinColumns = @JoinColumn(name = "ticket_id"), 
        inverseJoinColumns = @JoinColumn(name = "category_id"))
        private List<TicketCategory> ticketCategories;

    //Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public List<TicketLog> getTicketLogs() {
        return ticketLogs;
    }

    public void setTicketLogs(List<TicketLog> ticketLogs) {
        this.ticketLogs = ticketLogs;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<TicketCategory> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(List<TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    } 

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

 
   
}

// L'attributo cascade = CascadeType.ALL usato in JPA (Java Persistence API) specifica quali operazioni devono essere propagate da un'entità genitore alle entità figlie associate, in questo caso tutte

