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

import com.hireflow.dto.request.ScorecardTemplateRequestDTO;
import com.hireflow.dto.response.ScorecardTemplateResponseDTO;
import com.hireflow.service.ScorecardTemplateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/scorecard-templates")
public class ScorecardTemplateController {

    @Autowired
    private ScorecardTemplateService scorecardTemplateService;

    @PostMapping
    public ScorecardTemplateResponseDTO createScorecardTemplate(
    		@Valid @RequestBody ScorecardTemplateRequestDTO requestDTO) {

        return scorecardTemplateService.createScorecardTemplate(requestDTO);
    }

    @GetMapping("/{id}")
    public ScorecardTemplateResponseDTO getScorecardTemplateById(
            @PathVariable Long id) {

        return scorecardTemplateService.getScorecardTemplateById(id);
    }

    @GetMapping
    public List<ScorecardTemplateResponseDTO> getAllScorecardTemplates() {

        return scorecardTemplateService.getAllScorecardTemplates();
    }

    @PutMapping("/{id}")
    public ScorecardTemplateResponseDTO updateScorecardTemplate(
            @PathVariable Long id,
            @Valid @RequestBody ScorecardTemplateRequestDTO requestDTO) {

        return scorecardTemplateService.updateScorecardTemplate(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteScorecardTemplate(@PathVariable Long id) {

        scorecardTemplateService.deleteScorecardTemplate(id);
    }
    
    @GetMapping("/company/{companyId}")
    public List<ScorecardTemplateResponseDTO> getTemplatesByCompanyId(
            @PathVariable Long companyId) {

        return scorecardTemplateService.getTemplatesByCompanyId(companyId);
    }
}