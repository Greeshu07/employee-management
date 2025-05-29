package com.employee.employee_management.service;

import com.employee.employee_management.model.Employee;
import com.employee.employee_management.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public Employee addEmployee(Employee emp, MultipartFile idProofFile) throws Exception {
        // Validate age
        if (emp.getDob().isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("Employee must be older than 18");
        }

        // Generate employeeId
        String prefix = "EMP";
        long count = employeeRepository.count() + 1;
        emp.setEmployeeId(prefix + String.format("%05d", count));

        // Generate loginId
        String loginId = (emp.getFirstName().charAt(0) + emp.getLastName()).toLowerCase();
        while (employeeRepository.findByLoginId(loginId).isPresent()) {
            loginId = loginId + new Random().nextInt(900) + 100;
        }
        emp.setLoginId(loginId);

        // Save file
        if (!idProofFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + idProofFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, idProofFile.getBytes());
            emp.setIdProofFileName(fileName);
        }

        return employeeRepository.save(emp);
    }
    @Override
    public List<Employee> searchEmployees(String employeeId, String firstName, String lastName,
                                          String loginId, String dobFrom, String dobTo, String department) {

        List<Employee> allEmployees = employeeRepository.findAll(); 
        List<Employee> result = new ArrayList<>();

        for (Employee emp : allEmployees) {
            boolean match = true;

            if (employeeId != null && !employeeId.isEmpty() && !emp.getEmployeeId().contains(employeeId)) {
                match = false;
            }

            if (firstName != null && !firstName.isEmpty() &&
                    !emp.getFirstName().toLowerCase().contains(firstName.toLowerCase())) {
                match = false;
            }

            if (lastName != null && !lastName.isEmpty() &&
                    !emp.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                match = false;
            }

            if (loginId != null && !loginId.isEmpty() &&
                    !emp.getLoginId().toLowerCase().contains(loginId.toLowerCase())) {
                match = false;
            }

            if (department != null && !department.isEmpty() &&
                    !emp.getDepartment().equalsIgnoreCase(department)) {
                match = false;
            }

            // Optional: Handle dobFrom and dobTo
            if (dobFrom != null && !dobFrom.isEmpty()) {
                LocalDate fromDate = LocalDate.parse(dobFrom);
                if (emp.getDob().isBefore(fromDate)) {
                    match = false;
                }
            }

            if (dobTo != null && !dobTo.isEmpty()) {
                LocalDate toDate = LocalDate.parse(dobTo);
                if (emp.getDob().isAfter(toDate)) {
                    match = false;
                }
            }

            if (match) {
                result.add(emp);
            }
        }

        return result;
    }



    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> existing = employeeRepository.findById(id);
        if (existing.isPresent()) {
            employee.setId(id);
            return employeeRepository.save(employee);
        }
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void deleteMultipleEmployees(List<Long> ids) {
        employeeRepository.deleteAllById(ids);
    }

    @Override
    public List<String> getEmployeeHistory(Long id) {
        // Stubbed, replace with real logic if auditing enabled
        return List.of("Created", "Updated", "Viewed");
    }
}
