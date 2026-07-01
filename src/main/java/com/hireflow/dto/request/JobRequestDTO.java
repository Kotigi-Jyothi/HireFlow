package com.hireflow.dto.request;

import java.math.BigDecimal;

import com.hireflow.enums.JobStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequestDTO {

	@NotBlank(message = "Title is required")
	private String title;

	@NotBlank(message = "Department is required")
	private String department;

	@NotBlank(message = "Location is required")
	private String location;
	private String type;
	private Integer experienceRequired;
	private String skillsRequired;
	private BigDecimal salaryMin;
	private BigDecimal salaryMax;
	private Integer openingsCount;
	private JobStatus status;
	@NotNull(message = "Company id is required")
	private Long companyId;

	@NotNull(message = "Created by id is required")
	private Long createdById;
}
