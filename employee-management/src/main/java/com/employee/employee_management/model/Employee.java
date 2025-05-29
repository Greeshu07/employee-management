package com.employee.employee_management.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String employeeId; // Format: XXXXX1

    private String firstName;
    private String lastName;
    private String middleName;

    @Column(unique = true)
    private String loginId;

    private LocalDate dob;

    private String department;

    private Double salary;

    @Column(length = 1000)
    private String permanentAddress;

    @Column(length = 1000)
    private String currentAddress;

    private String idProofFileName;

    private Long createdBy; // User ID
}
