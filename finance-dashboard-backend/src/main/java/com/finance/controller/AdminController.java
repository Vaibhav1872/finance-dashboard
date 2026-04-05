package com.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.entity.User;
import com.finance.entity.Role;
import com.finance.repository.UserRepository;
import com.finance.repository.RoleRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {

        Role role = roleRepo.findByName(user.getRole().getName())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);
        user.setStatus("ACTIVE");

        return userRepo.save(user);
    }

    @PutMapping("/users/{id}/status")
    public User updateStatus(@PathVariable Long id, @RequestParam String status) {
        User user = userRepo.findById(id).get();
        user.setStatus(status);
        return userRepo.save(user);
    }
}