package com.example.TaskManagement.controller;

import com.example.TaskManagement.dto.UserDto;
import com.example.TaskManagement.exception.DuplicatedResourceException;
import com.example.TaskManagement.exception.ResourceNotFoundException;
import com.example.TaskManagement.response.ApiResponse;
import com.example.TaskManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("register")
    public ResponseEntity<ApiResponse> registration(@RequestBody UserDto userDto){
        try {
            userService.registration(userDto);
            return ResponseEntity.ok(new ApiResponse(null, "successful register"));
        } catch (DuplicatedResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(null, e.getMessage()));
        }

    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserDto userDto){
        try {
            String token = userService.login(userDto);
            return ResponseEntity.ok(new ApiResponse(token, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(null, e.getMessage()));
        }

    }
}
