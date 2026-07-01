package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.PipelineStageRequestDTO;
import com.hireflow.dto.response.PipelineStageResponseDTO;
import com.hireflow.enums.StageType;

public interface PipelineStageService {

    PipelineStageResponseDTO createPipelineStage(PipelineStageRequestDTO requestDTO);

    PipelineStageResponseDTO getPipelineStageById(Long id);

    List<PipelineStageResponseDTO> getAllPipelineStages();

    PipelineStageResponseDTO updatePipelineStage(Long id, PipelineStageRequestDTO requestDTO);

    void deletePipelineStage(Long id);
    
    List<PipelineStageResponseDTO> getStagesByCompanyId(Long companyId);

    List<PipelineStageResponseDTO> getStagesByStageType(StageType stageType);
}