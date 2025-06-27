package com.nihar.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRoleDTO {
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
}
