package com.hireflow.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardResponseDTO {

    private Long totalJobs;

    private Long totalCandidates;

    private Long totalApplications;

    private Long totalInterviews;

    private Long totalOfferLetters;

}