package com.example.ticket_platform.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "tickets/create"; // thymeleaf template index.html
    }
}
