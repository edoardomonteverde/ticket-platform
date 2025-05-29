package com.example.ticket_platform.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.TicketCategory;
import com.example.ticket_platform.repositories.TicketCategoryRepository;

@Service
public class TicketCategoryService {

    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;

       public List<TicketCategory> findAllCategories() {
        return ticketCategoryRepository.findAll();
    }

    public List<TicketCategory> findCategoriesByIds(List<Long> ids) {
        return ticketCategoryRepository.findAllById(ids);
    }


}
