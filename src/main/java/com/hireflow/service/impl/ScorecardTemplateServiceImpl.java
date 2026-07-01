package com.hireflow.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.ScorecardTemplateRequestDTO;
import com.hireflow.dto.response.ScorecardTemplateResponseDTO;
import com.hireflow.entity.Company;
import com.hireflow.entity.ScorecardSubmission;
import com.hireflow.entity.ScorecardTemplate;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.CompanyRepository;
import com.hireflow.repository.ScorecardSubmissionRepository;
import com.hireflow.repository.ScorecardTemplateRepository;
import com.hireflow.service.ScoreCalculationService;
import com.hireflow.service.ScorecardTemplateService;

@Service
public class ScorecardTemplateServiceImpl implements ScorecardTemplateService {

    @Autowired
    private ScorecardTemplateRepository scorecardTemplateRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ScoreCalculationService scoreCalculationService;

    @Autowired
    private ScorecardSubmissionRepository scorecardSubmissionRepository;
    @Override
    public ScorecardTemplateResponseDTO createScorecardTemplate(
            ScorecardTemplateRequestDTO requestDTO) {

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        ScorecardTemplate scorecardTemplate = new ScorecardTemplate();

        scorecardTemplate.setName(requestDTO.getName());
        scorecardTemplate.setCriteria(requestDTO.getCriteria());
        scorecardTemplate.setCompany(company);

        ScorecardTemplate savedTemplate =
                scorecardTemplateRepository.save(scorecardTemplate);

        return mapToResponse(savedTemplate);
    }

    @Override
    public ScorecardTemplateResponseDTO getScorecardTemplateById(Long id) {

        ScorecardTemplate scorecardTemplate =
                scorecardTemplateRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Scorecard Template not found"));

        return mapToResponse(scorecardTemplate);
    }

    @Override
    public List<ScorecardTemplateResponseDTO> getAllScorecardTemplates() {

        return scorecardTemplateRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ScorecardTemplateResponseDTO updateScorecardTemplate(
            Long id,
            ScorecardTemplateRequestDTO requestDTO) {

        ScorecardTemplate scorecardTemplate =
                scorecardTemplateRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Scorecard Template not found"));

        Company company =
                companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Company not found"));

        scorecardTemplate.setName(requestDTO.getName());
        scorecardTemplate.setCriteria(requestDTO.getCriteria());
        scorecardTemplate.setCompany(company);

        ScorecardTemplate updatedTemplate =
                scorecardTemplateRepository.save(scorecardTemplate);

        // Recalculate every scorecard using this template
        List<ScorecardSubmission> submissions =
                scorecardSubmissionRepository
                        .findByScorecardTemplateId(updatedTemplate.getId());

        for (ScorecardSubmission submission : submissions) {

            scoreCalculationService.recalculateScorecard(
                    submission.getId());
        }

        return mapToResponse(updatedTemplate);
    }

    @Override
    public void deleteScorecardTemplate(Long id) {

        scorecardTemplateRepository.deleteById(id);
    }

    private ScorecardTemplateResponseDTO mapToResponse(
            ScorecardTemplate scorecardTemplate) {

        ScorecardTemplateResponseDTO responseDTO =
                new ScorecardTemplateResponseDTO();

        responseDTO.setId(scorecardTemplate.getId());
        responseDTO.setName(scorecardTemplate.getName());
        responseDTO.setCriteria(scorecardTemplate.getCriteria());
        responseDTO.setCompanyId(scorecardTemplate.getCompany().getId());

        return responseDTO;
    }
    
    @Override
    public List<ScorecardTemplateResponseDTO> getTemplatesByCompanyId(Long companyId) {

        return scorecardTemplateRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}