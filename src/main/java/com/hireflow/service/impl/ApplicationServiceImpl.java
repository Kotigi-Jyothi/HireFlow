package com.hireflow.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.ApplicationRequestDTO;
import com.hireflow.dto.request.StageHistoryRequestDTO;
import com.hireflow.dto.response.ApplicationResponseDTO;
import com.hireflow.dto.response.CandidateRankingResponseDTO;
import com.hireflow.entity.Application;
import com.hireflow.entity.Interview;
import com.hireflow.entity.Job;
import com.hireflow.entity.PipelineStage;
import com.hireflow.entity.User;
import com.hireflow.enums.ApplicationStatus;
import com.hireflow.enums.InterviewStatus;
import com.hireflow.enums.UserRole;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.InterviewRepository;
import com.hireflow.repository.JobRepository;
import com.hireflow.repository.PipelineStageRepository;
import com.hireflow.repository.ScorecardSubmissionRepository;
import com.hireflow.repository.UserRepository;
import com.hireflow.service.ApplicationService;
import com.hireflow.service.PipelineStateMachine;
import com.hireflow.service.StageHistoryService;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PipelineStageRepository pipelineStageRepository;

    @Autowired
    private PipelineStateMachine pipelineStateMachine;
    
    @Autowired
    private StageHistoryService stageHistoryService;
    
    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private ScorecardSubmissionRepository scorecardSubmissionRepository;
    
    @Override
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO requestDTO) {

        Job job = jobRepository.findById(requestDTO.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        User candidate = userRepository.findById(requestDTO.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));
        
        //only candidated can apply for job
        if (candidate.getRole() != UserRole.CANDIDATE) {
            throw new RuntimeException("Only candidates can apply for jobs");
        }

        PipelineStage currentStage = pipelineStageRepository.findById(requestDTO.getCurrentStageId())
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline Stage not found"));

        Application application = new Application();

        application.setJob(job);
        application.setCandidate(candidate);
        application.setCurrentStage(currentStage);
        application.setStatus(requestDTO.getStatus());
        application.setOverallScore(requestDTO.getOverallScore());
        application.setResumePath(requestDTO.getResumePath());
        application.setAppliedAt(LocalDateTime.now());
        application.setLastStageChangedAt(LocalDateTime.now());
        application.setSlaBreached(false);

        Application savedApplication = applicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    @Override
    public ApplicationResponseDTO getApplicationById(Long id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        return mapToResponse(application);
    }

    @Override
    public List<ApplicationResponseDTO> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO requestDTO) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        Job job = jobRepository.findById(requestDTO.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        User candidate = userRepository.findById(requestDTO.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));

        PipelineStage nextStage = pipelineStageRepository.findById(requestDTO.getCurrentStageId())
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline Stage not found"));

        PipelineStage currentStage = application.getCurrentStage();

        //We need it for history.
        PipelineStage oldStage = currentStage;
        
        if (requestDTO.getStatus() != ApplicationStatus.REJECTED) {

            if (!pipelineStateMachine.canMove(currentStage, nextStage)) {

                throw new RuntimeException("Invalid stage transition");

            }

        }
        
        //candidate can move to next round, only if he clear's that round and scorecard is updated with that round marks
        if (requestDTO.getStatus() != ApplicationStatus.REJECTED) {

            List<Interview> interviews =
                    interviewRepository.findByApplicationIdAndStageId(
                            application.getId(),
                            currentStage.getId());

            if (interviews.isEmpty()) {

                throw new RuntimeException(
                        "Interview must be scheduled before moving to next stage");
            }

            Interview latestInterview =
                    interviews.get(interviews.size() - 1);

            if (latestInterview.getStatus()
                    != InterviewStatus.COMPLETED) {

                throw new RuntimeException(
                        "Interview must be completed before moving to next stage");
            }

            if (!scorecardSubmissionRepository
                    .existsByInterviewId(latestInterview.getId())) {

                throw new RuntimeException(
                        "Scorecard must be submitted before moving to next stage");
            }
        }
        application.setJob(job);
        application.setCandidate(candidate);
        application.setCurrentStage(nextStage);
        
     
        application.setStatus(requestDTO.getStatus());
        application.setOverallScore(requestDTO.getOverallScore());
        application.setResumePath(requestDTO.getResumePath());
        application.setLastStageChangedAt(LocalDateTime.now());

        Application updatedApplication = applicationRepository.save(application);

        if (!oldStage.getId().equals(nextStage.getId())) {

            StageHistoryRequestDTO historyDTO =
                    new StageHistoryRequestDTO();

            historyDTO.setApplicationId(application.getId());
            historyDTO.setFromStageId(oldStage.getId());
            historyDTO.setToStageId(nextStage.getId());
            historyDTO.setChangedById(3L);

            historyDTO.setNotes(
                    "Application moved from "
                            + oldStage.getStageName()
                            + " to "
                            + nextStage.getStageName());

            stageHistoryService.createStageHistory(historyDTO);
        }

        
        return mapToResponse(updatedApplication);
    }

    @Override
    public void deleteApplication(Long id) {

        applicationRepository.deleteById(id);
    }

    //Its job is to convert an Entity object into a ResponseDTO object.
    //mapToResponse():
//	✅ Converts Entity → DTO.
//	✅ Hides unnecessary fields.
//	✅ Prevents infinite loops caused by entity relationships.
//	✅ Gives you control over the JSON returned to the frontend.
//	✅ Keeps the Service methods clean and reusable.
    private ApplicationResponseDTO mapToResponse(Application application) {

        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO();

        responseDTO.setId(application.getId());
        responseDTO.setJobId(application.getJob().getId());
        responseDTO.setCandidateId(application.getCandidate().getId());
        responseDTO.setCurrentStageId(application.getCurrentStage().getId());
        responseDTO.setStatus(application.getStatus());
        responseDTO.setOverallScore(application.getOverallScore());
        responseDTO.setAppliedAt(application.getAppliedAt());
        responseDTO.setLastStageChangedAt(application.getLastStageChangedAt());
        responseDTO.setResumePath(application.getResumePath());
        responseDTO.setSlaBreached(
                application.getSlaBreached());

        return responseDTO;
    }
    @Override
    public List<ApplicationResponseDTO> getApplicationsByStatus(
            ApplicationStatus status) {

        return applicationRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public List<ApplicationResponseDTO> getApplicationsByCandidateId(
            Long candidateId) {

        return applicationRepository.findByCandidateId(candidateId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public List<ApplicationResponseDTO> getApplicationsByJobId(
            Long jobId) {

        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<CandidateRankingResponseDTO> getCandidateRanking(Long jobId) {

        List<Object[]> results =
                applicationRepository.rankCandidatesByJob(jobId);

        List<CandidateRankingResponseDTO> rankings =
                new ArrayList<>();

        for (Object[] row : results) {

            CandidateRankingResponseDTO dto =
                    new CandidateRankingResponseDTO();

            Long applicationId = ((Number) row[0]).longValue();

            Long candidateId = ((Number) row[1]).longValue();

            Double overallScore = ((Number) row[2]).doubleValue();

            Integer rank = ((Number) row[3]).intValue();

            Application application =
                    applicationRepository.findById(applicationId)
                    .orElseThrow();

            dto.setApplicationId(applicationId);
            dto.setCandidateId(candidateId);
            dto.setCandidateName(application.getCandidate().getName());
            dto.setOverallScore(overallScore);
            dto.setRank(rank);

            rankings.add(dto);
        }

        return rankings;
    }
    
    
   
}