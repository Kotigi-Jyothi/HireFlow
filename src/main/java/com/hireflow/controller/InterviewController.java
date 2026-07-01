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

import com.hireflow.dto.request.InterviewRequestDTO;
import com.hireflow.dto.response.InterviewResponseDTO;
import com.hireflow.enums.InterviewStatus;
import com.hireflow.service.InterviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping
    public InterviewResponseDTO createInterview(
    		@Valid @RequestBody InterviewRequestDTO requestDTO) {

        return interviewService.createInterview(requestDTO);
    }

    @GetMapping("/{id}")
    public InterviewResponseDTO getInterviewById(
            @PathVariable Long id) {

        return interviewService.getInterviewById(id);
    }

    @GetMapping
    public List<InterviewResponseDTO> getAllInterviews() {

        return interviewService.getAllInterviews();
    }

    @PutMapping("/{id}")
    public InterviewResponseDTO updateInterview(
            @PathVariable Long id,
            @Valid @RequestBody InterviewRequestDTO requestDTO) {

        return interviewService.updateInterview(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteInterview(@PathVariable Long id) {

        interviewService.deleteInterview(id);
    }
    
    
    //GET http://localhost:8080/api/interviews/interviewer/3
    //Returns all interviews assigned to interviewer with id = 3.
    @GetMapping("/interviewer/{interviewerId}")
    public List<InterviewResponseDTO> getInterviewsByInterviewerId(
            @PathVariable Long interviewerId) {

        return interviewService.getInterviewsByInterviewerId(interviewerId);
    }
    
    @GetMapping("/status/{status}")
    public List<InterviewResponseDTO> getInterviewsByStatus(
            @PathVariable InterviewStatus status) {

        return interviewService.getInterviewsByStatus(status);
    }
}