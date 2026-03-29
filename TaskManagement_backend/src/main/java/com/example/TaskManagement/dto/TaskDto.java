package com.example.TaskManagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private String title;
    private String description;
    private LocalDate dueDate;
}
