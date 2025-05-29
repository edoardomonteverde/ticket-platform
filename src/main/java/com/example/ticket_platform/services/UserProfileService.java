package com.example.ticket_platform.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.User;
import com.example.ticket_platform.models.UserProfile;
import com.example.ticket_platform.repositories.UserProfileRepository;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Optional<UserProfile> findByUserId(Long userId) {
        return userProfileRepository.findById(userId);
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public UserProfile getByUser(User user) {
    return userProfileRepository.findById(user.getId())
            .orElseThrow(() -> new RuntimeException("Profilo non trovato per l'utente con ID: " + user.getId()));
}
    public UserProfile findById(Long id) {
    return userProfileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profilo non trovato con ID: " + id));
}
}
