package com.nihar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardCounterDTO {
    private long employeeCount;
    private long presentEmployeeCount;
    private long absentEmployeeCount;
    private double totalSalary;
    private long totalTeams;
    private long totalDepartments;
}