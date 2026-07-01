package com.hireflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireflow.dto.request.ScorecardSubmissionRequestDTO;
import com.hireflow.dto.response.ScorecardSubmissionResponseDTO;
import com.hireflow.service.ScorecardSubmissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/scorecard-submissions")
public class ScorecardSubmissionController {

    @Autowired
    private ScorecardSubmissionService scorecardSubmissionService;

    @PostMapping
    public ScorecardSubmissionResponseDTO createScorecardSubmission(
    		@Valid @RequestBody ScorecardSubmissionRequestDTO requestDTO) {

        return scorecardSubmissionService.createScorecardSubmission(requestDTO);
    }

    @GetMapping("/{id}")
    public ScorecardSubmissionResponseDTO getScorecardSubmissionById(
            @PathVariable Long id) {

        return scorecardSubmissionService.getScorecardSubmissionById(id);
    }

    @GetMapping
    public List<ScorecardSubmissionResponseDTO> getAllScorecardSubmissions() {

        return scorecardSubmissionService.getAllScorecardSubmissions();
    }

    @PutMapping("/{id}")
    public ScorecardSubmissionResponseDTO updateScorecardSubmission(
            @PathVariable Long id,
            @Valid @RequestBody ScorecardSubmissionRequestDTO requestDTO) {

        return scorecardSubmissionService.updateScorecardSubmission(id, requestDTO);
    }

//    @DeleteMapping("/{id}")
//    public void deleteScorecardSubmission(@PathVariable Long id) {
//
//        scorecardSubmissionService.deleteScorecardSubmission(id);
//    }
    
    
    @GetMapping("/interview/{interviewId}")
    public List<ScorecardSubmissionResponseDTO> getScorecardsByInterviewId(
            @PathVariable Long interviewId) {

        return scorecardSubmissionService.getScorecardsByInterviewId(interviewId);
    }
    
    @GetMapping("/interviewer/{interviewerId}")
    public List<ScorecardSubmissionResponseDTO> getScorecardsByInterviewerId(
            @PathVariable Long interviewerId) {

        return scorecardSubmissionService.getScorecardsByInterviewerId(interviewerId);
    }
    
    @DeleteMapping("/{scorecardId}")
    public ResponseEntity<String> withdrawScorecard(
            @PathVariable Long scorecardId) {

        scorecardSubmissionService.withdrawScorecard(scorecardId);

        return ResponseEntity.ok("Scorecard withdrawn successfully.");
    }
}