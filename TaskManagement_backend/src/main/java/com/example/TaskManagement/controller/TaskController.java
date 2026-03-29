package com.example.TaskManagement.controller;

import com.example.TaskManagement.dto.TaskDto;
import com.example.TaskManagement.exception.DuplicatedResourceException;
import com.example.TaskManagement.response.ApiResponse;
import com.example.TaskManagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @PostMapping("add")
    public ResponseEntity<ApiResponse> addTask(@RequestBody TaskDto taskDto){
        try {
            taskService.addTask(taskDto);
            return ResponseEntity.ok(new ApiResponse(null, "Successfully added"));
        } catch (DuplicatedResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("all/{uncompleted}")
    public ResponseEntity<ApiResponse> getAllNotCompleted(@PathVariable boolean uncompleted){
        try {
            List<TaskDto> taskDtoList = taskService.getAllNotCompleted(uncompleted);
            return ResponseEntity.ok(new ApiResponse(taskDtoList));
        } catch (DuplicatedResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(null, e.getMessage()));
        }
    }

    @GetMapping("completed")
    public ResponseEntity<ApiResponse> completeTask(@RequestParam String title){
        try {
            taskService.completeTask(title);
            return ResponseEntity.ok(new ApiResponse(null, "Task completed"));
        } catch (DuplicatedResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(null, e.getMessage()));
        }
    }
}
