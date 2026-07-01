package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.ScorecardSubmissionRequestDTO;
import com.hireflow.dto.response.ScorecardSubmissionResponseDTO;

public interface ScorecardSubmissionService {

    ScorecardSubmissionResponseDTO createScorecardSubmission(ScorecardSubmissionRequestDTO requestDTO);

    ScorecardSubmissionResponseDTO getScorecardSubmissionById(Long id);

    List<ScorecardSubmissionResponseDTO> getAllScorecardSubmissions();

    ScorecardSubmissionResponseDTO updateScorecardSubmission(Long id, ScorecardSubmissionRequestDTO requestDTO);

    void deleteScorecardSubmission(Long id);
    
    List<ScorecardSubmissionResponseDTO> getScorecardsByInterviewId(Long interviewId);

    List<ScorecardSubmissionResponseDTO> getScorecardsByInterviewerId(Long interviewerId);

    void withdrawScorecard(Long scorecardId);
}