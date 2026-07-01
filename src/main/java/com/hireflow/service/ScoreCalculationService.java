package com.hireflow.service;

public interface ScoreCalculationService {
	
	
    
    void recalculateScorecard(Long scorecardSubmissionId);

    void recalculateApplication(Long applicationId);


}
