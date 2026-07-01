package com.hireflow.dto.response;

import java.time.LocalDateTime;

import com.hireflow.enums.Recommendation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScorecardSubmissionResponseDTO {

	private Long id;
	private Long interviewId;
	private Long interviewerId;
	private String scores;
	private Double overallRating;
	private Recommendation recommendation;
	private String strengths;
	private String weaknesses;
	private LocalDateTime submittedAt;
	//private Long scorecardTemplateId;
}
