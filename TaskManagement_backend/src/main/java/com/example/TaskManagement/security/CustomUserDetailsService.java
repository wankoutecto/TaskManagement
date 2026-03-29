package com.example.TaskManagement.security;

import com.example.TaskManagement.exception.ResourceNotFoundException;
import com.example.TaskManagement.model.UserLogin;
import com.example.TaskManagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email){
        UserLogin userlogin = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email does not exist."));
        return User.withUsername(userlogin.getEmail())
                .password(userlogin.getPassword())
                .roles(userlogin.getRoles().toArray(new String[0])).build();
    }
}
