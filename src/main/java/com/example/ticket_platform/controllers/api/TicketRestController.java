package com.example.ticket_platform.controllers.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticket_platform.models.Ticket;
import com.example.ticket_platform.repositories.TicketRepository;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketRepository ticketRepository;

    // API per ottenere l'elenco di tutti i ticket
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll(); // Restituisce tutti i ticket nel DB
    }

    // API per filtrare i ticket per categoria
    @GetMapping("/category/{category}")
    public List<Ticket> getTicketsByCategory(@PathVariable String category) {
        return ticketRepository.findByTicketCategoriesName(category); // Restituisce i ticket filtrati per categoria
    }

    // API per filtrare i ticket per stato
    @GetMapping("/status/{status}")
    public List<Ticket> getTicketsByStatus(@PathVariable String status) {
        return ticketRepository.findByStatus(status); // Restituisce i ticket filtrati per stato
    }

    // API per ottenere un ticket specifico tramite ID
    @GetMapping("/{id}")
    public Optional<Ticket> getTicketById(@PathVariable Long id) {
        return ticketRepository.findById(id); // Restituisce un ticket specifico per ID
    }
}
