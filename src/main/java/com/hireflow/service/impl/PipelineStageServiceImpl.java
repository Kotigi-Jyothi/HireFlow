package com.hireflow.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.PipelineStageRequestDTO;
import com.hireflow.dto.response.PipelineStageResponseDTO;
import com.hireflow.entity.Company;
import com.hireflow.entity.PipelineStage;
import com.hireflow.enums.StageType;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.CompanyRepository;
import com.hireflow.repository.PipelineStageRepository;
import com.hireflow.service.PipelineStageService;

@Service
public class PipelineStageServiceImpl implements PipelineStageService {

    @Autowired
    private PipelineStageRepository pipelineStageRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public PipelineStageResponseDTO createPipelineStage(PipelineStageRequestDTO requestDTO) {

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        PipelineStage pipelineStage = new PipelineStage();

        pipelineStage.setStageName(requestDTO.getStageName());
        pipelineStage.setSequenceOrder(requestDTO.getSequenceOrder());
        pipelineStage.setStageType(requestDTO.getStageType());
        pipelineStage.setSlaDays(requestDTO.getSlaDays());
        pipelineStage.setAutoEmailTemplate(requestDTO.getAutoEmailTemplate());
        pipelineStage.setCompany(company);

        PipelineStage savedStage = pipelineStageRepository.save(pipelineStage);

        return mapToResponse(savedStage);
    }

    @Override
    public PipelineStageResponseDTO getPipelineStageById(Long id) {

        PipelineStage pipelineStage = pipelineStageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline Stage not found"));

        return mapToResponse(pipelineStage);
    }

    @Override
    public List<PipelineStageResponseDTO> getAllPipelineStages() {

        return pipelineStageRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PipelineStageResponseDTO updatePipelineStage(Long id, PipelineStageRequestDTO requestDTO) {

        PipelineStage pipelineStage = pipelineStageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pipeline Stage not found"));

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        pipelineStage.setStageName(requestDTO.getStageName());
        pipelineStage.setSequenceOrder(requestDTO.getSequenceOrder());
        pipelineStage.setStageType(requestDTO.getStageType());
        pipelineStage.setSlaDays(requestDTO.getSlaDays());
        pipelineStage.setAutoEmailTemplate(requestDTO.getAutoEmailTemplate());
        pipelineStage.setCompany(company);

        PipelineStage updatedStage = pipelineStageRepository.save(pipelineStage);

        return mapToResponse(updatedStage);
    }

    @Override
    public void deletePipelineStage(Long id) {

        pipelineStageRepository.deleteById(id);
    }

    private PipelineStageResponseDTO mapToResponse(PipelineStage pipelineStage) {

        PipelineStageResponseDTO responseDTO = new PipelineStageResponseDTO();

        responseDTO.setId(pipelineStage.getId());
        responseDTO.setStageName(pipelineStage.getStageName());
        responseDTO.setSequenceOrder(pipelineStage.getSequenceOrder());
        responseDTO.setStageType(pipelineStage.getStageType());
        responseDTO.setSlaDays(pipelineStage.getSlaDays());
        responseDTO.setAutoEmailTemplate(pipelineStage.getAutoEmailTemplate());
        responseDTO.setCompanyId(pipelineStage.getCompany().getId());

        return responseDTO;
    }
    
    @Override
    public List<PipelineStageResponseDTO> getStagesByCompanyId(Long companyId) {

        return pipelineStageRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<PipelineStageResponseDTO> getStagesByStageType(StageType stageType) {

        return pipelineStageRepository.findByStageType(stageType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}