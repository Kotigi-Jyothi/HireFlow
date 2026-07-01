package com.hireflow.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.EmailLogRequestDTO;
import com.hireflow.dto.response.EmailLogResponseDTO;
import com.hireflow.entity.Application;
import com.hireflow.entity.EmailLog;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.EmailLogRepository;
import com.hireflow.service.EmailLogService;

@Service
public class EmailLogServiceImpl implements EmailLogService {

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public EmailLogResponseDTO createEmailLog(EmailLogRequestDTO requestDTO) {

        EmailLog emailLog = new EmailLog();

        // Application is optional
        if (requestDTO.getApplicationId() != null) {

            Application application = applicationRepository
                    .findById(requestDTO.getApplicationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

            emailLog.setApplication(application);
        }

        emailLog.setRecipient(requestDTO.getRecipient());
        emailLog.setSubject(requestDTO.getSubject());
        emailLog.setTemplateUsed(requestDTO.getTemplateUsed());
        emailLog.setStatus(requestDTO.getStatus());
        emailLog.setSentAt(LocalDateTime.now());

        EmailLog savedEmailLog = emailLogRepository.save(emailLog);

        return mapToResponse(savedEmailLog);
    }

    @Override
    public EmailLogResponseDTO getEmailLogById(Long id) {

        EmailLog emailLog = emailLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmailLog not found"));

        return mapToResponse(emailLog);
    }

    @Override
    public List<EmailLogResponseDTO> getAllEmailLogs() {

        return emailLogRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmailLogResponseDTO updateEmailLog(Long id, EmailLogRequestDTO requestDTO) {

        EmailLog emailLog = emailLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EmailLog not found"));

        Application application = applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        emailLog.setApplication(application);
        emailLog.setRecipient(requestDTO.getRecipient());
        emailLog.setSubject(requestDTO.getSubject());
        emailLog.setTemplateUsed(requestDTO.getTemplateUsed());
        emailLog.setStatus(requestDTO.getStatus());

        EmailLog updatedEmailLog = emailLogRepository.save(emailLog);

        return mapToResponse(updatedEmailLog);
    }

    @Override
    public void deleteEmailLog(Long id) {

        emailLogRepository.deleteById(id);
    }

    private EmailLogResponseDTO mapToResponse(EmailLog emailLog) {

        EmailLogResponseDTO responseDTO = new EmailLogResponseDTO();

        responseDTO.setId(emailLog.getId());
        if (emailLog.getApplication() != null) {
            responseDTO.setApplicationId(emailLog.getApplication().getId());
        }
       
        responseDTO.setRecipient(emailLog.getRecipient());
        responseDTO.setSubject(emailLog.getSubject());
        responseDTO.setTemplateUsed(emailLog.getTemplateUsed());
        responseDTO.setSentAt(emailLog.getSentAt());
        responseDTO.setStatus(emailLog.getStatus());

        return responseDTO;
    }
    
    @Override
    public List<EmailLogResponseDTO> getEmailLogsByApplicationId(Long applicationId) {

        return emailLogRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}