package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.OfferLetterRequestDTO;
import com.hireflow.dto.response.OfferLetterResponseDTO;

public interface OfferLetterService {

    OfferLetterResponseDTO createOfferLetter(OfferLetterRequestDTO requestDTO);

    OfferLetterResponseDTO getOfferLetterById(Long id);

    List<OfferLetterResponseDTO> getAllOfferLetters();

    OfferLetterResponseDTO updateOfferLetter(Long id, OfferLetterRequestDTO requestDTO);

    void deleteOfferLetter(Long id);
    
    OfferLetterResponseDTO getOfferLetterByApplicationId(Long applicationId);

    OfferLetterResponseDTO generatePdf(Long offerLetterId);
    
    OfferLetterResponseDTO signOfferLetter(
            String token,
            String ipAddress);
}