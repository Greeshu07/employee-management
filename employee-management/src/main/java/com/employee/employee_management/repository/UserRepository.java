package com.employee.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.employee_management.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
