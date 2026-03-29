package com.example.TaskManagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    @ManyToOne
    private UserLogin owner;
}
