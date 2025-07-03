package com.nihar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_role_department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    // 🔁 ManyToOne mapping with User
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    // 🔁 ManyToOne mapping with Role
    @ManyToOne(fetch = FetchType.EAGER, optional = false) // EAGER to prevent LazyInitializationException
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    // 🔁 ManyToOne mapping with Department
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    private String createdBy;
    private String updatedBy;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private boolean active;
}
