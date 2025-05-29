package com.example.ticket_platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ticket_platform.models.Role;
import com.example.ticket_platform.models.User;
import com.example.ticket_platform.services.RoleService;
import com.example.ticket_platform.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/user-management")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        List<Role> roles = roleService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("allRoles", roles);
        return "user/user-management";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam List<Long> roleIds) {

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsOnline(true);

        List<Role> selectedRoles = roleService.findByIds(roleIds);
        user.setRoles(selectedRoles);

        userService.save(user);

        return "redirect:/admin/user-management";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        List<Role> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);

        return "admin/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           @Valid @ModelAttribute("user") User userForm,
                           BindingResult result,
                           @RequestParam List<Long> roleIds) {

        if (result.hasErrors()) {
            return "admin/edit-user";
        }

        User existingUser = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));

        existingUser.setUsername(userForm.getUsername());
        existingUser.setEmail(userForm.getEmail());
        if (userForm.getPassword() != null && !userForm.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }

        List<Role> selectedRoles = roleService.findByIds(roleIds);
        existingUser.setRoles(selectedRoles);

        userService.save(existingUser);

        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
