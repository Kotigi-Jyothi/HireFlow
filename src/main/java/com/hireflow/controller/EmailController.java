package com.hireflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireflow.dto.request.EmailRequestDTO;
import com.hireflow.dto.request.InterviewInvitationRequestDTO;
import com.hireflow.dto.request.OfferLetterEmailRequestDTO;
import com.hireflow.dto.request.RejectionEmailRequestDTO;
import com.hireflow.dto.response.EmailResponseDTO;
import com.hireflow.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/get")
    public EmailResponseDTO getEmail(
            @RequestBody EmailRequestDTO requestDTO) {

        return emailService.sendEmail(requestDTO);
    }
    
    @PostMapping("/send")
    public EmailResponseDTO sendEmail(
            @RequestBody EmailRequestDTO requestDTO) {

        return emailService.sendEmail(requestDTO);
    }
    
    @PostMapping("/interview-invitation")
    public EmailResponseDTO sendInterviewInvitation(
            @RequestBody InterviewInvitationRequestDTO requestDTO) {

        return emailService.sendInterviewInvitation(requestDTO);
    }
    
    
    @PostMapping("/rejection")
    public EmailResponseDTO sendRejectionEmail(
            @RequestBody RejectionEmailRequestDTO requestDto) {

        emailService.sendRejectionEmail(requestDto);

        return emailService.sendRejectionEmail(requestDto);
    }

    @PostMapping("/offer-letter")
    public EmailResponseDTO sendOfferLetterEmail(
            @RequestBody OfferLetterEmailRequestDTO requestDto) {

        return emailService.sendOfferLetterEmail(requestDto);
    }

}