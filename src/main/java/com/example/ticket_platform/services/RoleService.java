package com.example.ticket_platform.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticket_platform.models.Role;
import com.example.ticket_platform.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public List<Role> findByIds(List<Long> ids) {
        return roleRepository.findByIdIn(ids);
    }
}