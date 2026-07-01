package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.EmailLogRequestDTO;
import com.hireflow.dto.response.EmailLogResponseDTO;

public interface EmailLogService {

    EmailLogResponseDTO createEmailLog(EmailLogRequestDTO requestDTO);

    EmailLogResponseDTO getEmailLogById(Long id);

    List<EmailLogResponseDTO> getAllEmailLogs();

    EmailLogResponseDTO updateEmailLog(Long id, EmailLogRequestDTO requestDTO);

    void deleteEmailLog(Long id);
    
    List<EmailLogResponseDTO> getEmailLogsByApplicationId(Long applicationId);
}