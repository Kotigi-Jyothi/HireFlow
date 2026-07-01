package com.hireflow.dto.response;

import java.time.LocalDateTime;

import com.hireflow.enums.InterviewStatus;
import com.hireflow.enums.InterviewType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewResponseDTO {

	private Long id;
	private Long applicationId;
	private Long interviewerId;
	private Long stageId;
	private LocalDateTime scheduledAt;
	private Integer durationMinutes;
	private InterviewType interviewType;
	private InterviewStatus status;
}
