package com.hireflow.dto.request;

import com.hireflow.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

	@NotBlank(message = "Name is required")
	private String name;

	@Email(message = "Invalid email")
	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Password is required")
	private String password;

	@NotNull(message = "Role is required")
	private UserRole role;

	@NotNull(message = "Active status is required")
	private Boolean isActive;

	@NotNull(message = "Company id is required")
	private Long companyId;
}
