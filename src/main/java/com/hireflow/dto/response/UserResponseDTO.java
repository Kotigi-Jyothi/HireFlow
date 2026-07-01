package com.hireflow.dto.response;

import java.time.LocalDateTime;

import com.hireflow.enums.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

	private Long id;
	private String name;
	private String email;
	private UserRole role;
	private Boolean isActive;
	private LocalDateTime createdAt;
	private Long companyId;
}
