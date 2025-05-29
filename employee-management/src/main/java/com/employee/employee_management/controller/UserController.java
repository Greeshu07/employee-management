package com.employee.employee_management.controller;

import com.employee.employee_management.model.User;
import com.employee.employee_management.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow frontend to access APIs
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user)
                ? "User registered successfully"
                : "Username already exists";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword())
                ? "Login successful"
                : "Invalid credentials";
    }
}
