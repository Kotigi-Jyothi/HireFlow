package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.JobRequestDTO;
import com.hireflow.dto.response.JobResponseDTO;
import com.hireflow.enums.JobStatus;

public interface JobService {

    JobResponseDTO createJob(JobRequestDTO requestDTO);

    JobResponseDTO getJobById(Long id);

    List<JobResponseDTO> getAllJobs();

    JobResponseDTO updateJob(Long id, JobRequestDTO requestDTO);

    void deleteJob(Long id);
    
    List<JobResponseDTO> getJobsByCompanyId(Long companyId);

    List<JobResponseDTO> getJobsByStatus(JobStatus status);

    List<JobResponseDTO> searchJobsByTitle(String title);
}