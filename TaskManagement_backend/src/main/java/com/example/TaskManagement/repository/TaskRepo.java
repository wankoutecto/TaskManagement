package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findAllByCompletedAndOwner_Email(boolean notCompleted, String email);
    Optional<Task> findByTitleAndOwner_Email(String title, String email);
}
