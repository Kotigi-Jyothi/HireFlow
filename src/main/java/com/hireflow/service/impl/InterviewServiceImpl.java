package com.hireflow.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.InterviewInvitationRequestDTO;
import com.hireflow.dto.request.InterviewRequestDTO;
import com.hireflow.dto.response.InterviewResponseDTO;
import com.hireflow.entity.Application;
import com.hireflow.entity.Interview;
import com.hireflow.entity.PipelineStage;
import com.hireflow.entity.User;
import com.hireflow.enums.InterviewStatus;
import com.hireflow.enums.UserRole;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.InterviewRepository;
import com.hireflow.repository.PipelineStageRepository;
import com.hireflow.repository.UserRepository;
import com.hireflow.service.CalendarInviteService;
import com.hireflow.service.EmailService;
import com.hireflow.service.InterviewService;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PipelineStageRepository pipelineStageRepository;
    
    @Autowired
    private CalendarInviteService calendarInviteService;
    
    @Autowired
    private EmailService emailService;
    
    private boolean hasTimeConflict(
            LocalDateTime start,
            Integer durationMinutes,
            List<Interview> interviews) {

        LocalDateTime end =
                start.plusMinutes(durationMinutes);

        for (Interview interview : interviews) {

            LocalDateTime existingStart =
                    interview.getScheduledAt();

            LocalDateTime existingEnd =
                    existingStart.plusMinutes(
                            interview.getDurationMinutes());

            boolean overlap =
                    start.isBefore(existingEnd)
                    &&
                    end.isAfter(existingStart);

            if (overlap) {
                return true;
            }
        }

        return false;
    }
    
    private List<LocalDateTime> suggestAlternativeSlots(
            Application application,
            User interviewer,
            LocalDateTime requestedTime,
            Integer durationMinutes) {

        List<LocalDateTime> suggestions =
                new ArrayList<>();

        List<Interview> interviewerInterviews =
                interviewRepository.findByInterviewerId(
                        interviewer.getId());

        List<Interview> candidateInterviews =
                interviewRepository.findByApplicationCandidateId(
                        application.getCandidate().getId());

        LocalDateTime candidateSlot =
                requestedTime.plusHours(1);

        int attempts = 0;

        while (suggestions.size() < 3 && attempts < 50) {

            LocalDateTime slotEnd =
                    candidateSlot.plusMinutes(durationMinutes);

            boolean interviewerBusy =
                    hasTimeConflict(
                            candidateSlot,
                            durationMinutes,
                            interviewerInterviews);

            boolean candidateBusy =
                    hasTimeConflict(
                            candidateSlot,
                            durationMinutes,
                            candidateInterviews);

            boolean withinBusinessHours =
                    !candidateSlot.toLocalTime()
                            .isBefore(LocalTime.of(9, 0))
                    &&
                    !slotEnd.toLocalTime()
                            .isAfter(LocalTime.of(18, 0));

            if (!interviewerBusy
                    && !candidateBusy
                    && withinBusinessHours) {

                suggestions.add(candidateSlot);
            }

            candidateSlot = candidateSlot.plusHours(1);
            attempts++;
        }

        return suggestions;
    }

    @Override
    public InterviewResponseDTO createInterview(InterviewRequestDTO requestDTO) {

        Application application = applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        User interviewer = userRepository.findById(requestDTO.getInterviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer not found"));
        

        if (interviewer.getRole() != UserRole.INTERVIEWER) {

            throw new RuntimeException(
                    "Only interviewers can conduct interviews");
        }

        LocalDateTime newStart =
                requestDTO.getScheduledAt();

        LocalDateTime newEnd =
                newStart.plusMinutes(
                        requestDTO.getDurationMinutes());

        List<Interview> interviewerInterviews =
                interviewRepository.findByInterviewerId(
                        interviewer.getId());

        if (hasTimeConflict(
                newStart,
                requestDTO.getDurationMinutes(),
                interviewerInterviews)) {

            List<LocalDateTime> suggestions =
                    suggestAlternativeSlots(
                            application,
                            interviewer,
                            newStart,
                            requestDTO.getDurationMinutes());

            throw new RuntimeException(
                    "Interviewer already has an interview during this time. "
                            + "Suggested slots: "
                            + suggestions);
        }

        List<Interview> candidateInterviews =
                interviewRepository.findByApplicationCandidateId(
                        application.getCandidate().getId());

        if (hasTimeConflict(
                newStart,
                requestDTO.getDurationMinutes(),
                candidateInterviews)) {

            List<LocalDateTime> suggestions =
                    suggestAlternativeSlots(
                            application,
                            interviewer,
                            newStart,
                            requestDTO.getDurationMinutes());

            throw new RuntimeException(
                    "Candidate already has an interview during this time. "
                            + "Suggested slots: "
                            + suggestions);
        }

        LocalTime businessStart =
                LocalTime.of(9, 0);

        LocalTime businessEnd =
                LocalTime.of(18, 0);

        if (newStart.toLocalTime().isBefore(businessStart)
                || newEnd.toLocalTime().isAfter(businessEnd)) {

            throw new RuntimeException(
                    "Interview must be fully within business hours (9 AM - 6 PM)");
        }

        if (newStart.isBefore(
                LocalDateTime.now().plusHours(24))) {

            throw new RuntimeException(
                    "Interview must be scheduled at least 24 hours in advance");
        }

        PipelineStage stage = pipelineStageRepository.findById(requestDTO.getStageId())
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        
       
        Interview interview = new Interview();

        interview.setApplication(application);
        interview.setInterviewer(interviewer);
        interview.setStage(stage);
        interview.setScheduledAt(requestDTO.getScheduledAt());
        interview.setDurationMinutes(requestDTO.getDurationMinutes());
        interview.setInterviewType(requestDTO.getInterviewType());
        interview.setStatus(requestDTO.getStatus());

        Interview savedInterview = interviewRepository.save(interview);
        
        System.out.println("Interview ID = " + savedInterview.getId());
        calendarInviteService.generateInterviewInvite(savedInterview);
        
        InterviewInvitationRequestDTO invitation =
                new InterviewInvitationRequestDTO();

        invitation.setApplicationId(application.getId());
        invitation.setInterviewId(savedInterview.getId());
        invitation.setInterviewDate(
                savedInterview.getScheduledAt().toString());

        emailService.sendInterviewInvitation(invitation);

        return mapToResponse(savedInterview);
    }

    @Override
    public InterviewResponseDTO getInterviewById(Long id) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        
        return mapToResponse(interview);
    }

    @Override
    public List<InterviewResponseDTO> getAllInterviews() {

        return interviewRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewResponseDTO updateInterview(Long id, InterviewRequestDTO requestDTO) {

        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview not found"));

        Application application = applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        User interviewer = userRepository.findById(requestDTO.getInterviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Interviewer not found"));

        PipelineStage stage = pipelineStageRepository.findById(requestDTO.getStageId())
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        interview.setApplication(application);
        interview.setInterviewer(interviewer);
        interview.setStage(stage);
        interview.setScheduledAt(requestDTO.getScheduledAt());
        interview.setDurationMinutes(requestDTO.getDurationMinutes());
        interview.setInterviewType(requestDTO.getInterviewType());
        interview.setStatus(requestDTO.getStatus());

        Interview updatedInterview = interviewRepository.save(interview);

        return mapToResponse(updatedInterview);
    }

    @Override
    public void deleteInterview(Long id) {

        interviewRepository.deleteById(id);
    }

    private InterviewResponseDTO mapToResponse(Interview interview) {

        InterviewResponseDTO responseDTO = new InterviewResponseDTO();

        responseDTO.setId(interview.getId());
        responseDTO.setApplicationId(interview.getApplication().getId());
        responseDTO.setInterviewerId(interview.getInterviewer().getId());
        responseDTO.setStageId(interview.getStage().getId());
        responseDTO.setScheduledAt(interview.getScheduledAt());
        responseDTO.setDurationMinutes(interview.getDurationMinutes());
        responseDTO.setInterviewType(interview.getInterviewType());
        responseDTO.setStatus(interview.getStatus());

        return responseDTO;
    }
    
    
    @Override
    public List<InterviewResponseDTO> getInterviewsByInterviewerId(Long interviewerId) {

        return interviewRepository.findByInterviewerId(interviewerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public List<InterviewResponseDTO> getInterviewsByStatus(InterviewStatus status) {

        return interviewRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}