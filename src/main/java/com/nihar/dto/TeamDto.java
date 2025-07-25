package com.nihar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Team Data Transfer Object")
public class TeamDto {

    private Long id;
    private String uuid;

    @NotBlank(message = "Team name is required")
    @Schema(description = "Team name", example = "Backend Team")
    private String name;

    @Schema(description = "Team description", example = "Responsible for backend services")
    private String description;
}
