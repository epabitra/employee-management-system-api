package com.nihar.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
	private Long id;
    private String uuid;
    private String name;
    private String description;
}
