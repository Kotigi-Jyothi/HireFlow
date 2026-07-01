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

import com.hireflow.dto.request.JobRequestDTO;
import com.hireflow.dto.response.JobResponseDTO;
import com.hireflow.enums.JobStatus;
import com.hireflow.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public JobResponseDTO createJob(@Valid @RequestBody JobRequestDTO requestDTO) {
        return jobService.createJob(requestDTO);
    }

    @GetMapping("/{id}")
    public JobResponseDTO getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @GetMapping
    public List<JobResponseDTO> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PutMapping("/{id}")
    public JobResponseDTO updateJob(@PathVariable Long id,
    		@Valid @RequestBody JobRequestDTO requestDTO) {
        return jobService.updateJob(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
    }
   
    
    @GetMapping("/company/{companyId}")
    public List<JobResponseDTO> getJobsByCompanyId(
            @PathVariable Long companyId) {

        return jobService.getJobsByCompanyId(companyId);
    }
    
    
    @GetMapping("/status/{status}")
    public List<JobResponseDTO> getJobsByStatus(
            @PathVariable JobStatus status) {

        return jobService.getJobsByStatus(status);
    }
    
    @GetMapping("/search/{title}")
    public List<JobResponseDTO> searchJobsByTitle(
            @PathVariable String title) {

        return jobService.searchJobsByTitle(title);
    }
}