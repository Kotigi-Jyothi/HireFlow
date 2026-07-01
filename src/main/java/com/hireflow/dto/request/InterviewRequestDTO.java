package com.hireflow.dto.request;

import java.time.LocalDateTime;

import com.hireflow.enums.InterviewStatus;
import com.hireflow.enums.InterviewType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewRequestDTO {

    @NotNull(message = "Application id is required")
    private Long applicationId;

    @NotNull(message = "Interviewer id is required")
    private Long interviewerId;

    @NotNull(message = "Stage id is required")
    private Long stageId;

    @NotNull(message = "Scheduled time is required")
    private LocalDateTime scheduledAt;

    @NotNull(message = "Duration is required")
    private Integer durationMinutes;

    @NotNull(message = "Interview type is required")
    private InterviewType interviewType;

    @NotNull(message = "Interview status is required")
    private InterviewStatus status;
}
