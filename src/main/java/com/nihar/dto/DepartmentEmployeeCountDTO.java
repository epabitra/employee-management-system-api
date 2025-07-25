package com.nihar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEmployeeCountDTO {
    private String departmentName;
    private long employeeCount;
}
