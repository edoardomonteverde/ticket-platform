package com.example.ticket_platform.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome utente non può essere vuoto.")
    @Size(min = 3, max = 30, message = "Il nome utente deve essere lungo tra 3 e 30 caratteri.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "La password non può essere vuota.")
    @Size(min = 8, message = "La password deve avere almeno 8 caratteri.")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "L'email non può essere vuota.")
    @Email(message = "L'email non è valida.")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isOnline = false;

   // Relazione ManyToMany con Role
    @ManyToMany(fetch = FetchType.EAGER)  // Carica immediatamente i ruoli
    @JoinTable(
        name = "user_roles",  // Nome della tabella di join
        joinColumns = @JoinColumn(name = "user_id"),  // Colonna per la relazione con l'utente
        inverseJoinColumns = @JoinColumn(name = "role_id")  // Colonna per la relazione con il ruolo
    )
    private List<Role> roles;

    // Modalità Ghost (solo per SuperAdmin)
    @Column(nullable = false)
    private boolean isGhostMode = false; // Default false: solo SuperAdmin può modificarlo


    // Relazione OneToOne con Admin (Un User può essere anche un Admin)
    @OneToOne(mappedBy = "user")
    private Admin admin;  

    // Relazione OneToOne con Operator (Un User può essere anche un Operator)
    @OneToOne(mappedBy = "user")
    private Operator operator; 

    // Relazione OneToOne con Client (Un User può essere anche un Client)
    @OneToOne(mappedBy = "user")
    private Client client;

    // Relazione OneToMany con Ticket (Un User può creare molti Ticket)
    @OneToMany(mappedBy = "createdBy")
    private List<Ticket> createdTickets;

    // Relazione OneToMany con Ticket (Un utente può avere molti ticket assegnati)
    @OneToMany(mappedBy = "assignedTo")
    private List<Ticket> assignedTickets;  // Ticket assegnati all'utente

    // Relazione OneToMany con TicketLog (Un User può creare molti TicketLog)
    @OneToMany(mappedBy = "user")
    private List<TicketLog> ticketLogs;


    // Getter e Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isGhostMode() {
        return isGhostMode;
    }

    public void setGhostMode(boolean isGhostMode) {
        this.isGhostMode = isGhostMode;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Ticket> getCreatedTickets() {
        return createdTickets;
    }

    public void setCreatedTickets(List<Ticket> createdTickets) {
        this.createdTickets = createdTickets;
    }

    public List<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(List<Ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }

    public List<TicketLog> getTicketLogs() {
        return ticketLogs;
    }

    public void setTicketLogs(List<TicketLog> ticketLogs) {
        this.ticketLogs = ticketLogs;
    }

  /*    // Metodo per verificare se l'utente è un admin
    public boolean isAdmin() {
        // Itera sui ruoli dell'utente e verifica se esiste un ruolo "ADMIN"
        for (Role role : this.roles) {
            if (role.getName().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    } */

    // Metodo per verificare se l'utente è un admin
    public boolean isAdmin(Authentication authentication) {
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

    for (GrantedAuthority authority : authorities) {
        if (authority.getAuthority().equals("ADMIN")) {
            return true;  // L'utente ha il ruolo "ADMIN"
        }
    }
    return false;  // L'utente non ha il ruolo "ADMIN"
}

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

   



  
    
}

