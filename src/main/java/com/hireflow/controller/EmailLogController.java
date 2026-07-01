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

import com.hireflow.dto.request.EmailLogRequestDTO;
import com.hireflow.dto.response.EmailLogResponseDTO;
import com.hireflow.service.EmailLogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/email-logs")
public class EmailLogController {

    @Autowired
    private EmailLogService emailLogService;

    @PostMapping
    public EmailLogResponseDTO createEmailLog(
    		@Valid @RequestBody EmailLogRequestDTO requestDTO) {

        return emailLogService.createEmailLog(requestDTO);
    }

    @GetMapping("/{id}")
    public EmailLogResponseDTO getEmailLogById(
            @PathVariable Long id) {

        return emailLogService.getEmailLogById(id);
    }

    @GetMapping
    public List<EmailLogResponseDTO> getAllEmailLogs() {

        return emailLogService.getAllEmailLogs();
    }

    @PutMapping("/{id}")
    public EmailLogResponseDTO updateEmailLog(
            @PathVariable Long id,
            @Valid @RequestBody EmailLogRequestDTO requestDTO) {

        return emailLogService.updateEmailLog(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEmailLog(@PathVariable Long id) {

        emailLogService.deleteEmailLog(id);
    }
    
    @GetMapping("/application/{applicationId}")
    public List<EmailLogResponseDTO> getEmailLogsByApplicationId(
            @PathVariable Long applicationId) {

        return emailLogService.getEmailLogsByApplicationId(applicationId);
    }
}