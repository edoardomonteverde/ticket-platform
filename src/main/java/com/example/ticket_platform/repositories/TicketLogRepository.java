package com.example.ticket_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.TicketLog;

@Repository
public interface TicketLogRepository extends JpaRepository<TicketLog, Long> {

    // Trova tutti i log per un ticket specifico
    List<TicketLog> findByTicketId(Long ticketId);

    // Trova tutti i log per un ticket specifico, ordinati per data (dal più recente)
    List<TicketLog> findByTicketIdOrderByTimestampDesc(Long ticketId);

    // Trova tutti i log creati da un determinato operatore (o admin)
    List<TicketLog> findByUserId(Long userId);

    // Trova tutti i log di un ticket per un determinato stato
    List<TicketLog> findByTicketIdAndStatus(Long ticketId, String status);
    
}
