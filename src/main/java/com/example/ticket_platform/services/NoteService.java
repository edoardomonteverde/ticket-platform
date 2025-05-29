package com.example.ticket_platform.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.Note;
import com.example.ticket_platform.models.Ticket;
import com.example.ticket_platform.repositories.NoteRepository;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public void save(Note note) {
        noteRepository.save(note);
    }

    public List<Note> getNotesForTicket(Ticket ticket) {
        return noteRepository.findByTicketOrderByCreatedAtDesc(ticket);
    }
}

