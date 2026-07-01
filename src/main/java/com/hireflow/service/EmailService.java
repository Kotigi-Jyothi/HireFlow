package com.hireflow.service;

import com.hireflow.dto.request.EmailRequestDTO;
import com.hireflow.dto.request.InterviewInvitationRequestDTO;
import com.hireflow.dto.request.OfferLetterEmailRequestDTO;
import com.hireflow.dto.request.RejectionEmailRequestDTO;
import com.hireflow.dto.request.SlaAlertEmailRequestDTO;
import com.hireflow.dto.response.EmailResponseDTO;

public interface EmailService {

    EmailResponseDTO sendEmail(EmailRequestDTO requestDTO);

    EmailResponseDTO sendInterviewInvitation(
            InterviewInvitationRequestDTO requestDTO);
    
    EmailResponseDTO sendRejectionEmail(RejectionEmailRequestDTO requestDto);

    EmailResponseDTO sendOfferLetterEmail(OfferLetterEmailRequestDTO requestDto);
    
    EmailResponseDTO sendSlaAlertEmail(
            SlaAlertEmailRequestDTO requestDTO);

}