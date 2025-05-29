package com.example.ticket_platform.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ticket_platform.models.Ticket;
import com.example.ticket_platform.models.TicketCategory;
import com.example.ticket_platform.models.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /* Queries personalizzate per gli attributi di Ticket */

    // Metodo per trovare tutti i ticket per stato
    List<Ticket> findByStatus(String status);

    // Metodo per trovare tutti i ticket creati da un utente specifico (ad esempio, cliente)
    List<Ticket> findByCreatedBy(User createdBy);

    // Metodo per trovare tutti i ticket assegnati a un operatore (oppure admin)
    List<Ticket> findByAssignedTo(User assignedTo);
    
    // Metodo per trovare ticket per categoria
    List<Ticket> findByTicketCategories(TicketCategory category);

    // Trova tutti i ticket associati ad una categoria specifica tramite l'ID della categoria
    List<Ticket> findByTicketCategoriesId(Long categoryId);

    // Trova tutti i ticket assegnati a un operatore (basato sull'ID dell'operatore)
    List<Ticket> findByAssignedToOperatorId(Long operatorId);

    // Trova i ticket creati dall'amministratore
    @Query("SELECT t FROM Ticket t WHERE t.createdBy.id = :adminId")
    List<Ticket> findByTicketsCreatedByAdmin(@Param("adminId") Long adminId);

    // Trova i ticket assegnati all'amministratore
    @Query("SELECT t FROM Ticket t WHERE t.assignedTo.id = :adminId")
    List<Ticket> findByTicketsAssignedToAdmin(@Param("adminId") Long adminId);

    // Metodo per cercare i ticket per stringa nel titolo (caso-insensibile)
    List<Ticket> findByTitleContainingIgnoreCase(String title);

     List<Ticket> findByTicketCategoriesName(String category);


}

