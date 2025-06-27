package com.nihar.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class FullUserDetailsDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String gender;
    private String designation;
    private double salary;
    private LocalDate joiningDate;
    private LocalDate dateOfBirth;
    private String imageUrl;
    private String timezoneId;
    private String langKey;
    private List<String> roles;
    private List<String> departments;
    private Set<String> authorities;
    
}
