package com.nihar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder // ✅ This enables the builder() method
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Department Data Transfer Object")
public class DepartmentDto {

    private Long id;
    private String uuid;

    @NotBlank(message = "Department name is required")
    @Schema(description = "Department name", example = "HR")
    private String name;

    @Schema(description = "Optional department description", example = "Handles recruitment and payroll")
    private String description;
}
