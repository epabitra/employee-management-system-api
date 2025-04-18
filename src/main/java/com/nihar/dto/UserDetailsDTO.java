package com.nihar.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailsDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String imageUrl;
    private String langKey;
    private String timezoneId;

    private String mobileNumber;
    private String gender;
    private String department;
    private String designation;
    private String role;

    private double salary;
    private Date joiningDate;
    private Date dateOfBirth;
}
