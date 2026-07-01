package com.hireflow.dto.response;

import java.math.BigDecimal;

import com.hireflow.enums.JobStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobResponseDTO {

	private Long id;
	private String title;
	private String department;
	private String location;
	private String type;
	private Integer experienceRequired;
	private String skillsRequired;
	private BigDecimal salaryMin;
	private BigDecimal salaryMax;
	private Integer openingsCount;
	private JobStatus status;
	private Long companyId;
	private Long createdById;
}
