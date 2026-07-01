package com.hireflow.dto.request;

import com.hireflow.enums.Recommendation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScorecardSubmissionRequestDTO {

    @NotNull(message = "Interview id is required")
    private Long interviewId;

    @NotNull(message = "Interviewer id is required")
    private Long interviewerId;

    @NotBlank(message = "Scores are required")
    private String scores;

//    @NotNull(message = "Overall rating is required")
//    private Double overallRating;

    @NotNull(message = "Recommendation is required")
    private Recommendation recommendation;

    private String strengths;

    private String weaknesses;
    
    @NotNull(message = "Scorecard Template Id is required")
    private Long scorecardTemplateId;
}
