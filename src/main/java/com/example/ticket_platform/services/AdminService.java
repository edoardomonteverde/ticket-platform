package com.example.ticket_platform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.Admin;
import com.example.ticket_platform.models.User;
import com.example.ticket_platform.repositories.AdminRepository;
import com.example.ticket_platform.repositories.UserRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    // Crea un nuovo Admin solo se l'utente corrente è un SuperAdmin
    public Admin createAdmin(Admin newAdmin, Long currentAdminId) {
        // Recupera l'admin
        Admin currentAdmin = adminRepository.findById(currentAdminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Verifica se l'admin corrente è un SuperAdmin
        if (!currentAdmin.isSuperAdmin()) {
            throw new RuntimeException("Only Super Admin can create new Admins");
        }

    
        // Creazione dell'utente per il nuovo admin
        User user = newAdmin.getUser();
        userRepository.save(user);

        // Salviamo il nuovo admin
        return adminRepository.save(newAdmin);
    }

}
