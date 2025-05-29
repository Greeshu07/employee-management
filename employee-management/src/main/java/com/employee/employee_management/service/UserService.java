package com.employee.employee_management.service;

import com.employee.employee_management.model.User;

public interface UserService {

    boolean register(User user);

    boolean login(String username, String password);
}
