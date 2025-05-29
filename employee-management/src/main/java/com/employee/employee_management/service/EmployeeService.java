package com.employee.employee_management.service;

import com.employee.employee_management.model.*;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    Employee addEmployee(Employee employee, MultipartFile idProofFile) throws Exception;
    List<Employee> searchEmployees(String employeeId, String firstName, String lastName, String loginId,
            String dobFrom, String dobTo, String department);
	Employee getEmployeeById(Long id);
	Employee updateEmployee(Long id, Employee employee);
	void deleteEmployee(Long id);
	void deleteMultipleEmployees(List<Long> ids);
	List<String> getEmployeeHistory(Long id);
}