package com.hireflow.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.EmailLogRequestDTO;
import com.hireflow.dto.request.EmailRequestDTO;
import com.hireflow.dto.request.InterviewInvitationRequestDTO;
import com.hireflow.dto.request.OfferLetterEmailRequestDTO;
import com.hireflow.dto.request.RejectionEmailRequestDTO;
import com.hireflow.dto.request.SlaAlertEmailRequestDTO;
import com.hireflow.dto.response.EmailResponseDTO;
import com.hireflow.entity.Application;
import com.hireflow.entity.User;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.OfferLetterRepository;
import com.hireflow.service.EmailLogService;
import com.hireflow.service.EmailService;
import com.hireflow.service.OfferLetterService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.UUID;

import com.hireflow.entity.OfferLetter;
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailLogService emailLogService;
    
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private OfferLetterRepository offerLetterRepository;

    @Autowired
    private OfferLetterService offerLetterService;
    
    @Override
    public EmailResponseDTO sendEmail(EmailRequestDTO requestDTO) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(requestDTO.getTo());
        message.setSubject(requestDTO.getSubject());
        message.setText(requestDTO.getBody());

        javaMailSender.send(message);
        
        //this saves email data in emailLog table
        EmailLogRequestDTO logRequest = new EmailLogRequestDTO();

     // applicationId null for generic emails
        logRequest.setApplicationId(null);

        logRequest.setRecipient(requestDTO.getTo());
        logRequest.setSubject(requestDTO.getSubject());
        logRequest.setTemplateUsed("CUSTOM");
        logRequest.setStatus("SENT");

        

        emailLogService.createEmailLog(logRequest);

        EmailResponseDTO responseDTO = new EmailResponseDTO();

        responseDTO.setMessage("Email sent successfully");

        return responseDTO;
    }
    
    @Override
    public EmailResponseDTO sendInterviewInvitation(InterviewInvitationRequestDTO requestDTO) {

        Application application = applicationRepository
                .findById(requestDTO.getApplicationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));

        User candidate = application.getCandidate();

        String body =
                "Dear " + candidate.getName() + ",\n\n" +
                "Congratulations!\n\n" +
                "You have been shortlisted for the next round.\n\n" +
                "Interview Date: " + requestDTO.getInterviewDate() + "\n\n" +
                "Best Regards,\n" +
                "HireFlow Team";

       // SimpleMailMessage message = new SimpleMailMessage();
        try {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //true=Email can contain attachments
        
        helper.setTo(candidate.getEmail());
        helper.setSubject("Interview Invitation");
        helper.setText(body);
        
        System.out.println("Looking for ICS file of interview = "
                + requestDTO.getInterviewId());
        
        File file =
                new File(
                        "uploads/calendar-invites/interview-"
                        + requestDTO.getInterviewId()
                        + ".ics");

        FileSystemResource resource =
                new FileSystemResource(file);

        helper.addAttachment(
                file.getName(),
                resource);

        javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {

            throw new RuntimeException(
                    "Failed to send interview invitation email", e);
        } 

        EmailLogRequestDTO logRequest = new EmailLogRequestDTO();

        logRequest.setApplicationId(application.getId());
        logRequest.setRecipient(candidate.getEmail());
        logRequest.setSubject("Interview Invitation");
        logRequest.setTemplateUsed("INTERVIEW_INVITATION");
        logRequest.setStatus("SENT");

        emailLogService.createEmailLog(logRequest);
        
        EmailResponseDTO responseDTO = new EmailResponseDTO();

        responseDTO.setMessage("Interview invitation sent successfully");

        return responseDTO;
    }
    
    
    @Override
    public EmailResponseDTO sendSlaAlertEmail(
            SlaAlertEmailRequestDTO requestDTO) {

        Application application =
                applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));

        User receiver =
                application.getJob().getCreatedBy();

        String body =
                "Dear " + receiver.getName() + ",\n\n"
                + "SLA has been breached.\n\n"
                + "Candidate : "
                + application.getCandidate().getName()
                + "\n"
                + "Job : "
                + application.getJob().getTitle()
                + "\n"
                + "Current Stage : "
                + application.getCurrentStage().getStageName()
                + "\n\n"
                + "Please review this application.\n\n"
                + "Regards,\nHireFlow";

   //// send email
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(receiver.getEmail());
        message.setSubject("SLA ha been reached");
        message.setText(body);

        javaMailSender.send(message);

        //  // create email log

        EmailLogRequestDTO logRequest = new EmailLogRequestDTO();

        logRequest.setApplicationId(application.getId());
        logRequest.setRecipient(receiver.getEmail());
        logRequest.setSubject("SLA has been reached");
        logRequest.setTemplateUsed("SLA_ALERT");
        logRequest.setStatus("SENT");

        emailLogService.createEmailLog(logRequest);
        
        EmailResponseDTO responseDTO = new EmailResponseDTO();

        responseDTO.setMessage("SLA has been reached");

        // return response
        return responseDTO;
    }
    
    @Override
    public EmailResponseDTO sendRejectionEmail(RejectionEmailRequestDTO requestDto) {

        Application application = applicationRepository
                .findById(requestDto.getApplicationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));

        User candidate = application.getCandidate();

        String body =
                "Dear " + candidate.getName() + ",\n\n" +
                "Thank you for your interest in our company.\n\n" +
                "After careful consideration, we regret to inform you that you have not been selected for this position.\n\n" +
                "We appreciate your time and effort and wish you success in your future endeavors.\n\n" +
                "Best Regards,\n" +
                "HireFlow Team";

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(candidate.getEmail());
        message.setSubject("Application Status Update");
        message.setText(body);

        javaMailSender.send(message);

        EmailLogRequestDTO logRequest = new EmailLogRequestDTO();

        logRequest.setApplicationId(application.getId());
        logRequest.setRecipient(candidate.getEmail());
        logRequest.setSubject("Application Status Update");
        logRequest.setTemplateUsed("REJECTION");
        logRequest.setStatus("SENT");

        emailLogService.createEmailLog(logRequest);
        
        EmailResponseDTO responseDTO = new EmailResponseDTO();

        responseDTO.setMessage("Rejection email sent successfully");

        return responseDTO;
    }
    
    @Override
    public EmailResponseDTO sendOfferLetterEmail(
            OfferLetterEmailRequestDTO requestDto) {

        OfferLetter offerLetter = offerLetterRepository
                .findById(requestDto.getOfferLetterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Offer Letter not found"));

        Application application = offerLetter.getApplication();

        User candidate = application.getCandidate();

        // Generate UUID if not already generated
        if (offerLetter.getSignatureToken() == null
                || offerLetter.getSignatureToken().isBlank()) {

            offerLetter.setSignatureToken(
                    UUID.randomUUID().toString());
        }

        // Generate PDF if not already generated
        if (offerLetter.getPdfPath() == null
                || offerLetter.getPdfPath().isBlank()) {

            offerLetterService.generatePdf(offerLetter.getId());

            offerLetter = offerLetterRepository
                    .findById(offerLetter.getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Offer Letter not found"));
        }

        String body =
                "Dear " + candidate.getName() + ",\n\n"
                + "Congratulations!\n\n"
                + "Please find your Offer Letter attached.\n\n"
                + "To accept your offer, click the link below:\n\n"
                + "http://localhost:8080/api/offer-letters/sign/"
                + offerLetter.getSignatureToken()
                + "\n\n"
                + "Regards,\n"
                + "HireFlow Team";

        try {

            MimeMessage mimeMessage =
                    javaMailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true);

            helper.setTo(candidate.getEmail());

            helper.setSubject("Offer Letter");

            helper.setText(body);

            FileSystemResource resource =
                    new FileSystemResource(
                            new File(offerLetter.getPdfPath()));

            helper.addAttachment(
                    "OfferLetter.pdf",
                    resource);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {

            throw new RuntimeException(
                    "Failed to send Offer Letter email", e);
        }

        offerLetter.setSentAt(LocalDateTime.now());

        offerLetterRepository.save(offerLetter);

        EmailLogRequestDTO logRequest = new EmailLogRequestDTO();

        logRequest.setApplicationId(application.getId());
        logRequest.setRecipient(candidate.getEmail());
        logRequest.setSubject("Offer Letter");
        logRequest.setTemplateUsed("OFFER_LETTER");
        logRequest.setStatus("SENT");

        emailLogService.createEmailLog(logRequest);

        EmailResponseDTO responseDTO = new EmailResponseDTO();

        responseDTO.setMessage("Offer letter email sent successfully.");

        return responseDTO;
    }

}