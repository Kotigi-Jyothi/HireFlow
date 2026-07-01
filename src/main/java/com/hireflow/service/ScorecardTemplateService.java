package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.ScorecardTemplateRequestDTO;
import com.hireflow.dto.response.ScorecardTemplateResponseDTO;

public interface ScorecardTemplateService {

    ScorecardTemplateResponseDTO createScorecardTemplate(ScorecardTemplateRequestDTO requestDTO);

    ScorecardTemplateResponseDTO getScorecardTemplateById(Long id);

    List<ScorecardTemplateResponseDTO> getAllScorecardTemplates();

    ScorecardTemplateResponseDTO updateScorecardTemplate(Long id, ScorecardTemplateRequestDTO requestDTO);

    void deleteScorecardTemplate(Long id);
    
    List<ScorecardTemplateResponseDTO> getTemplatesByCompanyId(Long companyId);
}