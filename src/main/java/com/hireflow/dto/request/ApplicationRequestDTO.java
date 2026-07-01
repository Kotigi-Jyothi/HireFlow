package com.hireflow.dto.request;

import com.hireflow.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequestDTO {

    @NotNull(message = "Job id is required")
    private Long jobId;

    @NotNull(message = "Candidate id is required")
    private Long candidateId;

    @NotNull(message = "Current stage id is required")
    private Long currentStageId;

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    private Double overallScore;

    private String resumePath;

}
