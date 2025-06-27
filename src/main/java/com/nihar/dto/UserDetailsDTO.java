package com.nihar.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private String designation;

    private double salary;
    private Date joiningDate;
    private Date dateOfBirth;

    private String roleUuid;
    private String departmentUuid;

    private List<String> roles; // ✅ Add this for output use (getAllUsers)
}
