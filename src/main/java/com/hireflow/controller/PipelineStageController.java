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

import com.hireflow.dto.request.PipelineStageRequestDTO;
import com.hireflow.dto.response.PipelineStageResponseDTO;
import com.hireflow.enums.StageType;
import com.hireflow.service.PipelineStageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pipeline-stages")
public class PipelineStageController {

    @Autowired
    private PipelineStageService pipelineStageService;

    @PostMapping
    public PipelineStageResponseDTO createPipelineStage(
    		@Valid @RequestBody PipelineStageRequestDTO requestDTO) {

        return pipelineStageService.createPipelineStage(requestDTO);
    }

    @GetMapping("/{id}")
    public PipelineStageResponseDTO getPipelineStageById(
            @PathVariable Long id) {

        return pipelineStageService.getPipelineStageById(id);
    }

    @GetMapping
    public List<PipelineStageResponseDTO> getAllPipelineStages() {

        return pipelineStageService.getAllPipelineStages();
    }

    @PutMapping("/{id}")
    public PipelineStageResponseDTO updatePipelineStage(
            @PathVariable Long id,
            @Valid @RequestBody PipelineStageRequestDTO requestDTO) {

        return pipelineStageService.updatePipelineStage(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deletePipelineStage(@PathVariable Long id) {

        pipelineStageService.deletePipelineStage(id);
    }
    
    @GetMapping("/company/{companyId}")
    public List<PipelineStageResponseDTO> getStagesByCompanyId(
            @PathVariable Long companyId) {

        return pipelineStageService.getStagesByCompanyId(companyId);
    }

    @GetMapping("/type/{stageType}")
    public List<PipelineStageResponseDTO> getStagesByStageType(
            @PathVariable StageType stageType) {

        return pipelineStageService.getStagesByStageType(stageType);
    }
}