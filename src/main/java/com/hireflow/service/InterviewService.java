package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.InterviewRequestDTO;
import com.hireflow.dto.response.InterviewResponseDTO;
import com.hireflow.enums.InterviewStatus;

public interface InterviewService {

    InterviewResponseDTO createInterview(InterviewRequestDTO requestDTO);

    InterviewResponseDTO getInterviewById(Long id);

    List<InterviewResponseDTO> getAllInterviews();

    InterviewResponseDTO updateInterview(Long id, InterviewRequestDTO requestDTO);

    void deleteInterview(Long id);
    
    List<InterviewResponseDTO> getInterviewsByInterviewerId(Long interviewerId);

    List<InterviewResponseDTO> getInterviewsByStatus(InterviewStatus status);
}