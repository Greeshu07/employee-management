package com.employee.employee_management.controller;

import com.employee.employee_management.model.Employee;
import com.employee.employee_management.service.EmployeeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public Employee addEmployee(
        @ModelAttribute Employee employee,
        @RequestParam("idProofFile") MultipartFile idProofFile
    ) throws Exception {
        return employeeService.addEmployee(employee, idProofFile);
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String dobFrom,
            @RequestParam(required = false) String dobTo,
            @RequestParam(required = false) String department
    ) {
        return employeeService.searchEmployees(employeeId, firstName, lastName, loginId, dobFrom, dobTo, department);
    }

    @GetMapping("/view/{id}")
    public Employee viewEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/edit/{id}")
    public Employee editEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @PostMapping("/delete-multiple")
    public void deleteMultipleEmployees(@RequestBody List<Long> ids) {
        employeeService.deleteMultipleEmployees(ids);
    }

    @GetMapping("/history/{id}")
    public List<String> getEmployeeHistory(@PathVariable Long id) {
        return employeeService.getEmployeeHistory(id);
    }
}
