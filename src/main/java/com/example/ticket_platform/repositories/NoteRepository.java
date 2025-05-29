package com.example.ticket_platform.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.Note;
import com.example.ticket_platform.models.Ticket;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // Trova tutte le note associate a un determinato ticket
    List<Note> findByTicketId(Long ticketId);

    // Trova tutte le note scritte da un determinato utente (Admin o Operator)
    List<Note> findByUserId(Long userId);

    // Trova tutte le note scritte su un determinato ticket e da un determinato utente
    List<Note> findByTicketIdAndUserId(Long ticketId, Long userId);

    // Trova tutte le note associate a un determinato ticket, ordinate per data (dal più recente)
    List<Note> findByTicketIdOrderByCreatedAtDesc(Long ticketId);

    List<Note> findByTicketOrderByCreatedAtDesc(Ticket ticket);
}
