package com.hireflow.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hireflow.dto.request.ScorecardSubmissionRequestDTO;
import com.hireflow.dto.response.ScorecardSubmissionResponseDTO;
import com.hireflow.entity.Application;
import com.hireflow.entity.Interview;
import com.hireflow.entity.ScorecardSubmission;
import com.hireflow.entity.ScorecardTemplate;
import com.hireflow.entity.User;
import com.hireflow.enums.UserRole;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.InterviewRepository;
import com.hireflow.repository.ScorecardSubmissionRepository;
import com.hireflow.repository.ScorecardTemplateRepository;
import com.hireflow.repository.UserRepository;
import com.hireflow.service.DraftScoreMemoryService;
import com.hireflow.service.ScoreCalculationService;
import com.hireflow.service.ScorecardSubmissionService;

@Service
public class ScorecardSubmissionServiceImpl implements ScorecardSubmissionService {

    @Autowired
    private ScorecardSubmissionRepository scorecardSubmissionRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private DraftScoreMemoryService draftScoreMemoryService;
	
    
    @Autowired
    private ScoreCalculationService scoreCalculationService;
	  @Autowired 
	  private ObjectMapper objectMapper;
	 
    
    @Autowired
    private ScorecardTemplateRepository scorecardTemplateRepository;

    @Override
    public ScorecardSubmissionResponseDTO createScorecardSubmission(ScorecardSubmissionRequestDTO requestDTO) {

        Interview interview = interviewRepository.findById(requestDTO.getInterviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        User interviewer = userRepository.findById(requestDTO.getInterviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer not found"));
        
    
        System.out.println(interviewer.getRole());

        if (interviewer.getRole() != UserRole.INTERVIEWER) {
        	System.out.println(interviewer.getRole());

            throw new RuntimeException(
                    "Only interviewers can submit scorecards");
        }
		
		  ScorecardTemplate template = scorecardTemplateRepository.findById(
		  requestDTO.getScorecardTemplateId()) .orElseThrow(() -> new
		  ResourceNotFoundException("Scorecard Template not found"));
		 
		  if (scorecardSubmissionRepository.existsByInterviewIdAndInterviewerId(
			        requestDTO.getInterviewId(),
			        requestDTO.getInterviewerId())) {

			    throw new RuntimeException(
			            "Scorecard has already been submitted by this interviewer for this interview.");
			}
       
        ScorecardSubmission submission = new ScorecardSubmission();

        submission.setInterview(interview);
        submission.setInterviewer(interviewer);
       submission.setScorecardTemplate(template);

        submission.setScores(requestDTO.getScores());
        //submission.setOverallRating(requestDTO.getOverallRating());
        submission.setRecommendation(requestDTO.getRecommendation());
        submission.setStrengths(requestDTO.getStrengths());
        submission.setWeaknesses(requestDTO.getWeaknesses());
        submission.setSubmittedAt(LocalDateTime.now());

        ScorecardSubmission savedSubmission =
                scorecardSubmissionRepository.save(submission);

        scoreCalculationService.recalculateScorecard(
                savedSubmission.getId());
        
        draftScoreMemoryService.removeDraft(
                savedSubmission.getInterview().getId());
        
        return mapToResponse(savedSubmission);
    }

    @Override
    public ScorecardSubmissionResponseDTO getScorecardSubmissionById(Long id) {

        ScorecardSubmission submission = scorecardSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scorecard Submission not found"));

        return mapToResponse(submission);
    }

    @Override
    public List<ScorecardSubmissionResponseDTO> getAllScorecardSubmissions() {

        return scorecardSubmissionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ScorecardSubmissionResponseDTO updateScorecardSubmission(
            Long id,
            ScorecardSubmissionRequestDTO requestDTO) {

        ScorecardSubmission submission = scorecardSubmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scorecard Submission not found"));

        Interview interview = interviewRepository.findById(requestDTO.getInterviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        User interviewer = userRepository.findById(requestDTO.getInterviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer not found"));

        submission.setInterview(interview);
        submission.setInterviewer(interviewer);
        submission.setScores(requestDTO.getScores());
       // submission.setOverallRating(requestDTO.getOverallRating());
        submission.setRecommendation(requestDTO.getRecommendation());
        submission.setStrengths(requestDTO.getStrengths());
        submission.setWeaknesses(requestDTO.getWeaknesses());

        ScorecardSubmission updatedSubmission =
                scorecardSubmissionRepository.save(submission);

        return mapToResponse(updatedSubmission);
    }

    @Override
    public void deleteScorecardSubmission(Long id) {

        scorecardSubmissionRepository.deleteById(id);
    }

    private ScorecardSubmissionResponseDTO mapToResponse(
            ScorecardSubmission submission) {

        ScorecardSubmissionResponseDTO responseDTO =
                new ScorecardSubmissionResponseDTO();

        responseDTO.setId(submission.getId());
        responseDTO.setInterviewId(submission.getInterview().getId());
        responseDTO.setInterviewerId(submission.getInterviewer().getId());
        responseDTO.setScores(submission.getScores());
        responseDTO.setOverallRating(submission.getOverallRating());
        responseDTO.setRecommendation(submission.getRecommendation());
        responseDTO.setStrengths(submission.getStrengths());
        responseDTO.setWeaknesses(submission.getWeaknesses());
        responseDTO.setSubmittedAt(submission.getSubmittedAt());

        return responseDTO;
    }
    
    @Override
    public List<ScorecardSubmissionResponseDTO> getScorecardsByInterviewId(Long interviewId) {

        return scorecardSubmissionRepository.findByInterviewId(interviewId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public List<ScorecardSubmissionResponseDTO> getScorecardsByInterviewerId(Long interviewerId) {

        return scorecardSubmissionRepository.findByInterviewerId(interviewerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
   
    @Override
    public void withdrawScorecard(Long scorecardId) {

        ScorecardSubmission submission =
                scorecardSubmissionRepository.findById(scorecardId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Scorecard not found"));

        Application application =
                submission.getInterview().getApplication();

        Long jobId = application.getJob().getId();

        // Delete scorecard
        scorecardSubmissionRepository.delete(submission);

        // Remaining scorecards
        List<ScorecardSubmission> remaining =
                scorecardSubmissionRepository
                        .findByInterviewApplicationId(
                                application.getId());

        if (remaining.isEmpty()) {

            application.setOverallScore(0.0);

        } else {

            double total = 0;

            for (ScorecardSubmission s : remaining) {

                total += s.getOverallRating();
            }

            application.setOverallScore(
                    total / remaining.size());
        }

        applicationRepository.save(application);

        System.out.println(
                "Overall Score Recalculated After Withdrawal");

        System.out.println(
                "Ranking Recalculated");

    }
}