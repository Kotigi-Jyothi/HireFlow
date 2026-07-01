package com.hireflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireflow.dto.request.StageHistoryRequestDTO;
import com.hireflow.dto.response.StageHistoryResponseDTO;
import com.hireflow.service.StageHistoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/stage-history")
public class StageHistoryController {

    @Autowired
    private StageHistoryService stageHistoryService;

    @PostMapping
    public StageHistoryResponseDTO createStageHistory(
    		@Valid @RequestBody StageHistoryRequestDTO requestDTO) {

        return stageHistoryService.createStageHistory(requestDTO);
    }

    @GetMapping("/{id}")
    public StageHistoryResponseDTO getStageHistoryById(
            @PathVariable Long id) {

        return stageHistoryService.getStageHistoryById(id);
    }

    @GetMapping
    public List<StageHistoryResponseDTO> getAllStageHistory() {

        return stageHistoryService.getAllStageHistories();
    }
    
    @GetMapping("/application/{applicationId}")
    public List<StageHistoryResponseDTO> getStageHistoryByApplicationId(
            @PathVariable Long applicationId) {

        return stageHistoryService.getStageHistoryByApplicationId(applicationId);
    }
}