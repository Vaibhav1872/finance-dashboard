package com.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.finance.entity.User;
import com.finance.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user, "ROLE_VIEWER");
    }

    @GetMapping("/login")
    public String login() {
        return "Login successful";
    }
}