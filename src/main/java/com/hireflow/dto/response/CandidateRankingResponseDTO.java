package com.hireflow.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRankingResponseDTO {

    private Long applicationId;

    private Long candidateId;

    private String candidateName;

    private Double overallScore;

    private Integer rank;
}