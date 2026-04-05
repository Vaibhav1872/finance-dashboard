package com.finance.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.finance.entity.User;
import com.finance.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                user.getStatus().equals("ACTIVE"), // status check
                true,
                true,
                true,
                Collections.singleton(
                        new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                user.getRole().getName()
                        )
                )
        );
    }
}