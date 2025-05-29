package com.example.ticket_platform.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ticket_platform.models.Note;
import com.example.ticket_platform.models.Ticket;
import com.example.ticket_platform.models.TicketPriority;
import com.example.ticket_platform.models.User;
import com.example.ticket_platform.services.NoteService;
import com.example.ticket_platform.services.OperatorService;
import com.example.ticket_platform.services.TicketCategoryService;
import com.example.ticket_platform.services.TicketService;
import com.example.ticket_platform.services.UserService;

import jakarta.validation.Valid; 

@Controller
@RequestMapping("/tickets")
public class TicketController {
    
    @Autowired
    private NoteService noteService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketCategoryService categoryService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
public String home() {
    return "tickets/create";
}

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("categoryList", categoryService.findAllCategories());
        model.addAttribute("operatorList", operatorService.getAllOperators());
        model.addAttribute("priorityList", TicketPriority.values());
        model.addAttribute("statusList", List.of("APERTO", "IN ATTESA", "CHIUSO"));
        return "tickets/create";
    }

   @PostMapping("/create")
public String store(@Valid @ModelAttribute("ticket") Ticket formTicket,
                    BindingResult bindingResult,
                    @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
                    Principal principal,
                    Model model,
                    RedirectAttributes redirectAttributes) {

    User currentUser = userService.findByUsername(principal.getName());

    if (bindingResult.hasErrors()) {
        model.addAttribute("categoryList", categoryService.findAllCategories());

        if (currentUser.hasRole("ADMIN")) {
            model.addAttribute("operatorList", operatorService.getAllOperators());
            model.addAttribute("priorityList", TicketPriority.values());
        }

        return "redirect:tickets/create";
    }

    // Imposta dati comuni
    formTicket.setCreatedAt(LocalDateTime.now());
    formTicket.setCreatedBy(currentUser);
    formTicket.setTicketCategories(categoryService.findCategoriesByIds(categoryIds));

    // Comportamento diverso per clienti vs admin
    if (currentUser.hasRole("CLIENT")) {
        formTicket.setPriority(TicketPriority.MEDIA); // default
        formTicket.setStatus("APERTO");
        formTicket.setAssignedTo(null); // non può assegnare
    }

    if (currentUser.hasRole("ADMIN")) {
        // Admin può impostare tutti i valori nel form
    }

    ticketService.save(formTicket);

    redirectAttributes.addFlashAttribute("successMessage", "Ticket creato con successo!");
    return "redirect:/tickets";

    }


    @GetMapping
    public String listTickets(Model model, Authentication authentication) {
       User currentUser = userService.findByUsername(authentication.getName());
        List<Ticket> tickets;

        if (currentUser.hasRole("CLIENT")) {
            return "redirect:/access-denied";
        }

        if (currentUser.hasRole("ADMIN") && currentUser.getAdmin() != null && currentUser.getAdmin().isSuperAdmin()) {
            tickets = ticketService.findAll();
        } else {
            tickets = ticketService.findByAssignedTo(currentUser);
        }

        model.addAttribute("tickets", tickets);
        return "tickets/index";
    }

     @GetMapping("/view/{id}")
    public String viewTicket(@PathVariable Long id, Model model, Authentication authentication) {
        Ticket ticket = ticketService.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User currentUser = userService.findByUsername(authentication.getName());

         boolean assignedTo = ticket.getAssignedTo() != null &&
                             ticket.getAssignedTo().getId().equals(currentUser.getId());

        boolean isSuperAdmin = currentUser.getAdmin() != null &&
                               currentUser.getAdmin().isSuperAdmin();


        if (currentUser.hasRole("CLIENT") ||
           ((currentUser.hasRole("OPERATOR") || currentUser.hasRole("ADMIN")) && !assignedTo && !isSuperAdmin)) {
            return "redirect:/access-denied";
        }

        model.addAttribute("ticket", ticket);
        return "tickets/view";
    }

    @PostMapping("/{id}/add-note")
public String addNote(@PathVariable Long id,
                      @RequestParam("noteContent") String noteContent,
                      Principal principal,
                      RedirectAttributes redirectAttributes) {

    User currentUser = userService.findByUsername(principal.getName());
    Ticket ticket = ticketService.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));

    Note note = new Note();
    note.setContent(noteContent);
    note.setCreatedAt(LocalDateTime.now());
    note.setTicket(ticket);
    note.setUser(currentUser);

    noteService.save(note);

    redirectAttributes.addFlashAttribute("successMessage", "Nota aggiunta con successo!");
    return "redirect:/tickets/" + id;
}

}

    



