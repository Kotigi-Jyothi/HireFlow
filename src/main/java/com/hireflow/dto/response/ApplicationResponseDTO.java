package com.hireflow.dto.response;

import java.time.LocalDateTime;

import com.hireflow.enums.ApplicationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationResponseDTO {

	private Long id;
	private Long jobId;
	private Long candidateId;
	private Long currentStageId;
	private ApplicationStatus status;
	private Double overallScore;
	private LocalDateTime appliedAt;
	private LocalDateTime lastStageChangedAt;
	private String resumePath;
	private Boolean slaBreached;
}
