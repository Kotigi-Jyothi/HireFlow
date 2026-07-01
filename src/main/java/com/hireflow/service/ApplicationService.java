package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.ApplicationRequestDTO;
import com.hireflow.dto.response.ApplicationResponseDTO;
import com.hireflow.dto.response.CandidateRankingResponseDTO;
import com.hireflow.enums.ApplicationStatus;

public interface ApplicationService {

    ApplicationResponseDTO createApplication(ApplicationRequestDTO requestDTO);

    ApplicationResponseDTO getApplicationById(Long id);

    List<ApplicationResponseDTO> getAllApplications();

    ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO requestDTO);

    void deleteApplication(Long id);
    
    //custom api/search
    
    List<ApplicationResponseDTO> getApplicationsByStatus(
            ApplicationStatus status);

    List<ApplicationResponseDTO> getApplicationsByCandidateId(
            Long candidateId);

    List<ApplicationResponseDTO> getApplicationsByJobId(
            Long jobId);
    
    List<CandidateRankingResponseDTO> getCandidateRanking(Long jobId);
}