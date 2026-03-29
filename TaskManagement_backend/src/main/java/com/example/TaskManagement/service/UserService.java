package com.example.TaskManagement.service;

import com.example.TaskManagement.dto.UserDto;
import com.example.TaskManagement.exception.DuplicatedResourceException;
import com.example.TaskManagement.mapper.UserMapper;
import com.example.TaskManagement.model.UserLogin;
import com.example.TaskManagement.repository.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtService jwtService;
    public void registration(UserDto userDto){
        if(userRepo.existsByEmailIgnoreCase(userDto.getEmail()))
            throw new DuplicatedResourceException("Email already exists.");
        System.out.println(userDto.getEmail());;
        UserLogin user = userMapper.mappedToUser(userDto);
        userRepo.save(user);
    }

    public String login(UserDto userDto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword()));
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        return jwtService.generateToken(userDetails);
    }
}
