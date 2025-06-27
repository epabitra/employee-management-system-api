package com.nihar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String uuid;
    private String email;
    private String passwordHash;
    private String imageUrl;
    private String langKey;
    private String timezoneId;

    private String mobileNumber; // Renamed from 'phone' for consistency
    private String gender;
    private String department;
    private String designation;

    private double salary;
    private LocalDate joiningDate;
    private LocalDate dateOfBirth;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRoleDepartment> userRoleDepartment;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Salary> salaries;
}
