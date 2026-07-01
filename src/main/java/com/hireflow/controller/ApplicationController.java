package com.hireflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireflow.dto.request.ApplicationRequestDTO;
import com.hireflow.dto.response.ApplicationResponseDTO;
import com.hireflow.dto.response.CandidateRankingResponseDTO;
import com.hireflow.enums.ApplicationStatus;
import com.hireflow.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ApplicationResponseDTO createApplication(
    		@Valid @RequestBody ApplicationRequestDTO requestDTO) {

        return applicationService.createApplication(requestDTO);
    }

    @GetMapping("/{id}")
    public ApplicationResponseDTO getApplicationById(
            @PathVariable Long id) {

        return applicationService.getApplicationById(id);
    }

    @GetMapping
    public List<ApplicationResponseDTO> getAllApplications() {

        return applicationService.getAllApplications();
    }

    @PutMapping("/{id}")
    public ApplicationResponseDTO updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationRequestDTO requestDTO) {

        return applicationService.updateApplication(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {

        applicationService.deleteApplication(id);
    }
    
    @GetMapping("/status/{status}")
    public List<ApplicationResponseDTO> getApplicationsByStatus(
            @PathVariable ApplicationStatus status) {

        return applicationService.getApplicationsByStatus(status);
    }
    
    @GetMapping("/candidate/{candidateId}")
    public List<ApplicationResponseDTO> getApplicationsByCandidateId(
            @PathVariable Long candidateId) {

        return applicationService.getApplicationsByCandidateId(
                    candidateId);
    }
    
    @GetMapping("/job/{jobId}")
    public List<ApplicationResponseDTO> getApplicationsByJobId(
            @PathVariable Long jobId) {

        return applicationService.getApplicationsByJobId(jobId);
    }
    
    @GetMapping("/ranking/{jobId}")
    public List<CandidateRankingResponseDTO> getCandidateRanking(
            @PathVariable Long jobId) {

        return applicationService.getCandidateRanking(jobId);
    }
}