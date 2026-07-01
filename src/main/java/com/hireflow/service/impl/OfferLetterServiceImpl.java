package com.hireflow.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.OfferLetterRequestDTO;
import com.hireflow.dto.response.OfferLetterResponseDTO;
import com.hireflow.entity.Application;
import com.hireflow.entity.OfferLetter;
import com.hireflow.enums.OfferStatus;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.OfferLetterRepository;
import com.hireflow.service.OfferLetterService;
import com.hireflow.util.PdfGeneratorUtil;

@Service
public class OfferLetterServiceImpl implements OfferLetterService {

    @Autowired
    private OfferLetterRepository offerLetterRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PdfGeneratorUtil pdfGeneratorUtil;
    
    
    
    @Override
    public OfferLetterResponseDTO createOfferLetter(OfferLetterRequestDTO requestDTO) {

        Application application = applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        OfferLetter offerLetter = new OfferLetter();

        offerLetter.setApplication(application);
        offerLetter.setContent(requestDTO.getContent());
        offerLetter.setPdfPath(requestDTO.getPdfPath());
        offerLetter.setSignatureToken(requestDTO.getSignatureToken());
        offerLetter.setStatus(requestDTO.getStatus());
        offerLetter.setSentAt(LocalDateTime.now());
        offerLetter.setSalary(requestDTO.getSalary());
        offerLetter.setJoiningDate(requestDTO.getJoiningDate());
        
        OfferLetter savedOfferLetter = offerLetterRepository.save(offerLetter);

        return mapToResponse(savedOfferLetter);
    }

    @Override
    public OfferLetterResponseDTO getOfferLetterById(Long id) {

        OfferLetter offerLetter = offerLetterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer letter not found"));

        return mapToResponse(offerLetter);
    }

    @Override
    public List<OfferLetterResponseDTO> getAllOfferLetters() {

        return offerLetterRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OfferLetterResponseDTO updateOfferLetter(Long id, OfferLetterRequestDTO requestDTO) {

        OfferLetter offerLetter = offerLetterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer letter not found"));

        Application application = applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        offerLetter.setApplication(application);
        offerLetter.setContent(requestDTO.getContent());
        offerLetter.setPdfPath(requestDTO.getPdfPath());
        offerLetter.setSignatureToken(requestDTO.getSignatureToken());
        offerLetter.setStatus(requestDTO.getStatus());
        offerLetter.setSalary(requestDTO.getSalary());
        offerLetter.setJoiningDate(requestDTO.getJoiningDate());

        if (requestDTO.getStatus().name().equals("ACCEPTED")) {
            offerLetter.setSignedAt(LocalDateTime.now());
        }

        OfferLetter updatedOfferLetter = offerLetterRepository.save(offerLetter);

        return mapToResponse(updatedOfferLetter);
    }

    @Override
    public void deleteOfferLetter(Long id) {

        offerLetterRepository.deleteById(id);
    }

    private OfferLetterResponseDTO mapToResponse(OfferLetter offerLetter) {

        OfferLetterResponseDTO responseDTO = new OfferLetterResponseDTO();

        responseDTO.setId(offerLetter.getId());
        responseDTO.setApplicationId(offerLetter.getApplication().getId());
        responseDTO.setContent(offerLetter.getContent());
        responseDTO.setPdfPath(offerLetter.getPdfPath());
        responseDTO.setSignatureToken(offerLetter.getSignatureToken());
        responseDTO.setStatus(offerLetter.getStatus());
        responseDTO.setSentAt(offerLetter.getSentAt());
        responseDTO.setSignedAt(offerLetter.getSignedAt());
        responseDTO.setSalary(offerLetter.getSalary());
        responseDTO.setJoiningDate(offerLetter.getJoiningDate());

        return responseDTO;
    }
    
    @Override
    public OfferLetterResponseDTO getOfferLetterByApplicationId(Long applicationId) {

        OfferLetter offerLetter = offerLetterRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer letter not found"));

        return mapToResponse(offerLetter);
    }
    
    private String replacePlaceholders(OfferLetter offerLetter) {

        Application application = offerLetter.getApplication();

        String content = offerLetter.getContent();

        content = content.replace(
                "{{candidate_name}}",
                application.getCandidate().getName());

        content = content.replace(
                "{{job_title}}",
                application.getJob().getTitle());

        content = content.replace(
                "{{department}}",
                application.getJob().getDepartment());

        content = content.replace(
                "{{location}}",
                application.getJob().getLocation());

        content = content.replace(
                "{{company_name}}",
                application.getJob().getCompany().getName());

        content = content.replace(
                "{{salary}}",
                offerLetter.getSalary().toString());

        content = content.replace(
                "{{joining_date}}",
                offerLetter.getJoiningDate().toString());

        content = content.replace(
                "{{offer_date}}",
                LocalDate.now().toString());

        return content;
    }
    @Override
    public OfferLetterResponseDTO generatePdf(Long offerLetterId) {

        OfferLetter offerLetter =
                offerLetterRepository.findById(offerLetterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Offer Letter not found"));

        try {

        	String finalContent = replacePlaceholders(offerLetter);

        	String pdfPath =
                    pdfGeneratorUtil.generateOfferLetterPdf(
                            offerLetter.getId(),
                            finalContent);

            offerLetter.setPdfPath(pdfPath);

            OfferLetter updatedOfferLetter =
                    offerLetterRepository.save(offerLetter);

            return mapToResponse(updatedOfferLetter);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generating PDF : " + e.getMessage());
        }
    }
    
    @Override
    public OfferLetterResponseDTO signOfferLetter(
            String token,
            String ipAddress) {

        OfferLetter offerLetter =
                offerLetterRepository.findBySignatureToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid signature token"));

        offerLetter.setSignedAt(LocalDateTime.now());

        offerLetter.setSignedIp(ipAddress);

        offerLetter.setStatus(OfferStatus.ACCEPTED);

        OfferLetter savedOfferLetter =
                offerLetterRepository.save(offerLetter);

        return mapToResponse(savedOfferLetter);
    }
}