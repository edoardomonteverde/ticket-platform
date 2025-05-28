package com.example.ticket_platform.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.Operator;
import com.example.ticket_platform.models.Role;
import com.example.ticket_platform.models.Ticket;
import com.example.ticket_platform.models.User;
import com.example.ticket_platform.repositories.OperatorRepository;
import com.example.ticket_platform.repositories.TicketRepository;
import com.example.ticket_platform.repositories.UserRepository;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private TicketRepository ticketRepository;

     @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Modifica lo stato dell'operatore se non ha ticket in stato 'da fare' o 'in corso'
    public void updateOperatorStatus(Long operatorId, boolean newStatus) {
        // Recupera l'operatore
        Optional<Operator> optionalOperator = operatorRepository.findById(operatorId);
        
        if (optionalOperator.isPresent()) {
            Operator operator = optionalOperator.get();
            
            // Verifica se l'operatore ha ticket in stato "da fare" o "in corso"
            List<Ticket> assignedTickets = ticketRepository.findByAssignedToOperatorId(operatorId);
            
            boolean hasActiveTicket = false;
            
            
            for (Ticket ticket : assignedTickets) {
                if (ticket.getStatus().equals("DA_FARE") || ticket.getStatus().equals("IN_CORSO")) {
                    hasActiveTicket = true;
                    break;  // Esce dal ciclo non appena trova il primo ticket attivo
                }
            }
            
            if (hasActiveTicket) {
                throw new RuntimeException("Cannot update status because the operator has active tickets.");
            } else {
                // Aggiorna lo stato dell'operatore
                operator.setActivityStatus(newStatus);  // newStatus è un booleano
                operatorRepository.save(operator);
            }
        } else {
            throw new RuntimeException("Operator not found.");
        }
    }
    // Metodo per creare un nuovo operatore (solo se l'admin è autenticato)
    public Operator createOperator(Long adminId, Long userId, String username, String password, String department, String specialization) {
        // Verifica che l'admin esista e che abbia i diritti necessari per creare un operatore
        Optional<User> adminOpt = userRepository.findById(adminId);
        if (adminOpt.isPresent()) {
            User admin = adminOpt.get();
            
            // Controlla se l'admin ha il ruolo "ADMIN"
            boolean isAdmin = false;
            for (Role role : admin.getRoles()) {
                if (role.getName().equals("ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }
            
            if (!isAdmin) {
                throw new RuntimeException("L'utente non ha i diritti necessari per creare un operatore");
            }

            // Verifica se l'utente da associare esiste, altrimenti lo crea
            Optional<User> userOpt = userRepository.findById(userId);
            User user;
            
            if (userOpt.isPresent()) {
                user = userOpt.get(); // Se l'utente esiste, lo recupera
            } else {
                // Se l'utente non esiste, creiamo un nuovo utente
                user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));  // Usa il PasswordEncoder per la sicurezza
                // Assicurati che tu abbia un ruolo, ad esempio "USER"
                Role userRole = new Role();
                userRole.setName("USER"); // O il ruolo che desideri
                user.setRoles(Arrays.asList(userRole));
                userRepository.save(user); // Salva l'utente nel database
            }
            
            // Crea e salva l'operatore
            Operator operator = new Operator();
            operator.setUser(user);
            operator.setDepartment(department);  // Imposta il dipartimento
            operator.setSpecialization(specialization);  // Imposta la specializzazione
            operator.setActivityStatus(true);  // Imposta lo stato attivo di default
            
            return operatorRepository.save(operator); // Salva l'operatore
        } else {
            throw new RuntimeException("Admin non trovato o privilegi insufficienti");
        }
    }

    // Restituisce tutti i ticket assegnati a un operatore specifico
    public List<Ticket> getTicketsAssignedToOperator(Long operatorId) {
        // Trova i ticket assegnati all'operatore tramite il suo ID
        return ticketRepository.findByAssignedToOperatorId(operatorId);
    }

     // Metodo per ottenere tutti gli operatori
    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();
    }


    // Metodo che restituisce tutti gli operatori filtrati per stato di attività
    public List<Operator> getOperatorsByActivityStatus(boolean activityStatus) {
        return operatorRepository.findByActivityStatus(activityStatus);
    }
   
}


