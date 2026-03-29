package com.example.TaskManagement.mapper;

import com.example.TaskManagement.dto.UserDto;
import com.example.TaskManagement.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserLogin mappedToUser(UserDto userDto){
        UserLogin user = new UserLogin();
        user.setEmail(userDto.getEmail());
        String hashPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(hashPassword);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        return user;
    }
}
