package com.example.ticket_platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ticket_platform.models.User;
import com.example.ticket_platform.models.UserProfile;
import com.example.ticket_platform.services.UserProfileService;
import com.example.ticket_platform.services.UserService;

@Controller
@RequestMapping("/profile")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    // Visualizza profilo
    @GetMapping
    public String viewProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        UserProfile profile = userProfileService.findById(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("profile", profile);
        return "user/user-profile";  // Thymeleaf template: profile/view.html
    }
}

