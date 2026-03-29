package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserLogin, Integer> {
    boolean existsByEmailIgnoreCase(String email);

    Optional<UserLogin> findByEmail(String email);
}
