package com.employee.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.employee.employee_management.model.Employee;


import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByLoginId(String loginId);
    Optional<Employee> findByEmployeeId(String employeeId);

}
