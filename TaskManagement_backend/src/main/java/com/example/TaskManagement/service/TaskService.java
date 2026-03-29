package com.example.TaskManagement.service;

import com.example.TaskManagement.dto.TaskDto;
import com.example.TaskManagement.exception.ResourceNotFoundException;
import com.example.TaskManagement.mapper.TaskMapper;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.UserLogin;
import com.example.TaskManagement.repository.TaskRepo;
import com.example.TaskManagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    TaskRepo taskRepo;

    //helper function
    public UserLogin getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("There is no logged User"));
    }
    public void addTask(TaskDto taskDto) {
        System.out.println(taskDto.getTitle() + " " + taskDto.getDescription() + " " + taskDto.getDueDate());
        UserLogin owner = getCurrentUser();
        Task task = taskMapper.mappedToTask(owner, taskDto);
        taskRepo.save(task);
    }

    public List<TaskDto> getAllNotCompleted(boolean notCompleted){
        UserLogin owner = getCurrentUser();
        List<Task> taskList = taskRepo.findAllByCompletedAndOwner_Email(notCompleted, owner.getEmail());
        return taskMapper.mappedToListTaskDto(taskList);
    }

    public void completeTask(String title){
        UserLogin owner = getCurrentUser();
        Task task = taskRepo.findByTitleAndOwner_Email(title, owner.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Task does not exist"));
        task.setCompleted(true);
        taskRepo.save(task);
    }
}
