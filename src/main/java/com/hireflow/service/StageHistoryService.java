package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.StageHistoryRequestDTO;
import com.hireflow.dto.response.StageHistoryResponseDTO;

public interface StageHistoryService {

    StageHistoryResponseDTO createStageHistory(StageHistoryRequestDTO requestDTO);

    StageHistoryResponseDTO getStageHistoryById(Long id);

    List<StageHistoryResponseDTO> getAllStageHistories();

    StageHistoryResponseDTO updateStageHistory(Long id, StageHistoryRequestDTO requestDTO);

    void deleteStageHistory(Long id);
    
    List<StageHistoryResponseDTO> getStageHistoryByApplicationId(Long applicationId);
}