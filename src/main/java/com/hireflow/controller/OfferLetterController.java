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
import jakarta.servlet.http.HttpServletRequest;

import com.hireflow.dto.request.OfferLetterRequestDTO;
import com.hireflow.dto.response.OfferLetterResponseDTO;
import com.hireflow.service.OfferLetterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/offer-letters")
public class OfferLetterController {

    @Autowired
    private OfferLetterService offerLetterService;

    @PostMapping
    public OfferLetterResponseDTO createOfferLetter(
    		@Valid @RequestBody OfferLetterRequestDTO requestDTO) {

        return offerLetterService.createOfferLetter(requestDTO);
    }

    @GetMapping("/{id}")
    public OfferLetterResponseDTO getOfferLetterById(
            @PathVariable Long id) {

        return offerLetterService.getOfferLetterById(id);
    }

    @GetMapping
    public List<OfferLetterResponseDTO> getAllOfferLetters() {

        return offerLetterService.getAllOfferLetters();
    }

    @PutMapping("/{id}")
    public OfferLetterResponseDTO updateOfferLetter(
            @PathVariable Long id,
            @Valid @RequestBody OfferLetterRequestDTO requestDTO) {

        return offerLetterService.updateOfferLetter(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOfferLetter(@PathVariable Long id) {

        offerLetterService.deleteOfferLetter(id);
    }
    
    @GetMapping("/application/{applicationId}")
    public OfferLetterResponseDTO getOfferLetterByApplicationId(
            @PathVariable Long applicationId) {

        return offerLetterService.getOfferLetterByApplicationId(applicationId);
    }
    
    @PostMapping("/{offerLetterId}/generate-pdf")
    public OfferLetterResponseDTO generatePdf(
            @PathVariable Long offerLetterId) {

        return offerLetterService.generatePdf(offerLetterId);
    }
    
    @PostMapping("/sign/{token}")
    public OfferLetterResponseDTO signOfferLetter(
            @PathVariable String token,
            jakarta.servlet.http.HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        return offerLetterService.signOfferLetter(
                token,
                ipAddress);
    }
}