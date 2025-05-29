package com.example.ticket_platform.services;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.Department;
import com.example.ticket_platform.models.Note;
import com.example.ticket_platform.models.Ticket;
import com.example.ticket_platform.models.TicketCategory;
import com.example.ticket_platform.models.TicketLog;
import com.example.ticket_platform.models.User;
import com.example.ticket_platform.repositories.NoteRepository;
import com.example.ticket_platform.repositories.TicketCategoryRepository;
import com.example.ticket_platform.repositories.TicketLogRepository;
import com.example.ticket_platform.repositories.TicketRepository;
import com.example.ticket_platform.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketLogRepository ticketLogRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

   


    // Creazione di un ticket da parte di un cliente
    @Transactional
    public Ticket createTicketByClient(Ticket ticket, Long clientId) {
    User client = userRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("Client not found"));

    // Impostiamo il cliente come creatore del ticket
    ticket.setCreatedBy(client);

    // Recuperiamo la prima categoria e il dipartimento associato
    TicketCategory firstCategory = ticket.getTicketCategories().get(0);
    // Usare il repository per recuperare la categoria
    TicketCategory category = ticketCategoryRepository.findById(firstCategory.getId())
            .orElseThrow(() -> new RuntimeException("Category not found"));
    
    // Otteniamo il dipartimento associato alla categoria
    Department department = category.getDepartment();  // Dipartimento associato alla categoria
    // Otteniamo l'amministratore del dipartimento
    User admin = department.getAdmin();  // Amministratore associato al dipartimento
    ticket.setAssignedTo(admin);  // Assegniamo il ticket all'amministratore

    // Salviamo il ticket
    Ticket savedTicket = ticketRepository.save(ticket);

    // Registriamo un log per la creazione del ticket
    TicketLog log = new TicketLog();
    log.setTicket(savedTicket);
    log.setStatus("CREATED");
    log.setUser(client);
    ticketLogRepository.save(log);


    return savedTicket;
}
    // Creazione di un ticket da parte di un amministratore
    @Transactional
    public Ticket createTicketByAdmin(Ticket ticket, Long adminId, Long operatorId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        User operator = userRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        // Impostiamo l'amministratore come creatore del ticket
        ticket.setCreatedBy(admin);

        // Assegniamo il ticket all'operatore
        ticket.setAssignedTo(operator);

        // Salviamo il ticket
        Ticket savedTicket = ticketRepository.save(ticket);

        // Registriamo un log per la creazione del ticket
        TicketLog log = new TicketLog();
        log.setTicket(savedTicket);
        log.setStatus("CREATED");
        log.setUser(admin);
        ticketLogRepository.save(log);


        return savedTicket;
    }

    // Assegnare un ticket ad un operatore
    @Transactional
    public Ticket assignTicketToOperator(Long ticketId, Long operatorId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        
        User operator = userRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found"));
        
        // Assegniamo l'operatore al ticket
        ticket.setAssignedTo(operator);

        // Salviamo il ticket aggiornato
        Ticket updatedTicket = ticketRepository.save(ticket);

        // Registriamo un log per l'assegnazione
        TicketLog log = new TicketLog();
        log.setTicket(updatedTicket);
        log.setStatus("ASSIGNED");
        log.setUser(operator);
        ticketLogRepository.save(log);

        return updatedTicket;
    }

    // Aggiornamento dello stato del ticket
    @Transactional
    public Ticket updateTicketStatus(Long ticketId, String status) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(status);

        // Salviamo il ticket aggiornato
        Ticket updatedTicket = ticketRepository.save(ticket);

        // Registriamo un log per l'aggiornamento dello stato
        TicketLog log = new TicketLog();
        log.setTicket(updatedTicket);
        log.setStatus("STATUS_UPDATED");
        log.setUser(ticket.getAssignedTo()); // L'operatore che aggiorna lo stato
        ticketLogRepository.save(log);

        return updatedTicket;
    }

public Ticket getTicketDetails(Long ticketId, Authentication authentication) {
    // Recupera il ticket in base all'ID
    Optional<Ticket> ticket = ticketRepository.findById(ticketId);
    if (ticket.isPresent()) {
        Ticket foundTicket = ticket.get();

        // Controllo se l'utente è autenticato
        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }

        // Ottieni il ruolo dell'utente dal contesto di sicurezza
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        boolean isOperator = authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_OPERATOR"));
        Long userId = ((User) authentication.getPrincipal()).getId();  // Ottieni l'ID dell'utente autenticato

        // Se l'utente è un admin, può vedere qualsiasi ticket
        if (isAdmin) {
            return foundTicket;
        }

        // Se l'utente è un operatore, può vedere solo i ticket assegnati a lui
        if (isOperator) {
            if (foundTicket.getAssignedTo() != null && foundTicket.getAssignedTo().getId().equals(userId)) {
                return foundTicket;
            } else {
                throw new RuntimeException("Ticket is not assigned to this operator");
            }
        }

        // Se l'utente è un cliente, può vedere solo i ticket che ha creato
        if (foundTicket.getCreatedBy().getId().equals(userId)) {
            return foundTicket;
        } else {
            throw new RuntimeException("Ticket not found or you do not have permission to view it.");
        }
    } else {
        throw new RuntimeException("Ticket not found");
    }
}

       


    @Transactional
public Note addNoteToTicket(Long ticketId, Long userId, String content) {
    // Recupera il ticket dal repository
    Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket not found"));

    // Recupera l'utente dal repository (sia Admin che Operator o Cliente)
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Creiamo una nuova nota per il ticket
    Note note = new Note();
    note.setTicket(ticket);  // Colleghiamo la noo della notata al ticket
    note.setUser(user);      // Impostiamo l'utente che aggiunge la nota
    note.setContent(content);  // Aggiungiamo il contenuto

    // Salviamo la nota nel database
    Note savedNote = noteRepository.save(note);

    // Registriamo un log per la creazione della nota
    TicketLog log = new TicketLog();
    log.setTicket(ticket);
    log.setStatus("NOTE ADDED");
    log.setUser(user);
    ticketLogRepository.save(log);

    return savedNote;  // Restituiamo la nota appena aggiunta
}

    // Restituisce tutti i ticket
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Metodo per recuperare tutti i ticket assegnati ad un admin
    public List<Ticket> getTicketsAssignedToAdmin(Long adminId, Authentication authentication) {
        // Verifica che l'utente sia un admin
        if (!userService.isAdmin(authentication)) {
            throw new RuntimeException("Unauthorized: Only admins can view these tickets.");
        }

        // Trova tutti i ticket assegnati all'admin specificato
        // Assumendo che l'admin sia associato a un User
        Optional<User> adminUserOpt = userRepository.findById(adminId);
        if (adminUserOpt.isPresent()) {
            User adminUser = adminUserOpt.get();
            
            // Recupera tutti i ticket assegnati all'admin tramite il campo 'assignedTo'
            return ticketRepository.findByAssignedTo(adminUser);
        } else {
            throw new RuntimeException("Admin not found");
        }
    }

    // Metodo per cercare i ticket in base alla stringa nel titolo
    public List<Ticket> searchTicketsByTitle(String title) {
        // Cerca i ticket usando la stringa del titolo
        return ticketRepository.findByTitleContainingIgnoreCase(title);
    }

      public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

       public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

     public List<Ticket> findByAssignedTo(User user) {
        return ticketRepository.findByAssignedTo(user);
     }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    
    }
}





   
