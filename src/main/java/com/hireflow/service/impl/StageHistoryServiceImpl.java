package com.hireflow.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.StageHistoryRequestDTO;
import com.hireflow.dto.response.StageHistoryResponseDTO;
import com.hireflow.entity.Application;
import com.hireflow.entity.PipelineStage;
import com.hireflow.entity.StageHistory;
import com.hireflow.entity.User;
import com.hireflow.enums.UserRole;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.PipelineStageRepository;
import com.hireflow.repository.StageHistoryRepository;
import com.hireflow.repository.UserRepository;
import com.hireflow.service.StageHistoryService;

@Service
public class StageHistoryServiceImpl implements StageHistoryService {

    @Autowired
    private StageHistoryRepository stageHistoryRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PipelineStageRepository pipelineStageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public StageHistoryResponseDTO createStageHistory(StageHistoryRequestDTO requestDTO) {

        Application application = applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        PipelineStage fromStage = pipelineStageRepository.findById(requestDTO.getFromStageId())
                .orElseThrow(() -> new ResourceNotFoundException("From stage not found"));

        PipelineStage toStage = pipelineStageRepository.findById(requestDTO.getToStageId())
                .orElseThrow(() -> new ResourceNotFoundException("To stage not found"));

        User changedBy = userRepository.findById(requestDTO.getChangedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        

        if (changedBy.getRole() != UserRole.HR_MANAGER &&
            changedBy.getRole() != UserRole.ADMIN) {

            throw new RuntimeException(
                    "Only HR_MANAGER or ADMIN can change stages");
        }

        StageHistory stageHistory = new StageHistory();

        stageHistory.setApplication(application);
        stageHistory.setFromStage(fromStage);
        stageHistory.setToStage(toStage);
        stageHistory.setChangedBy(changedBy);
        stageHistory.setChangedAt(LocalDateTime.now());
        stageHistory.setNotes(requestDTO.getNotes());

        StageHistory savedStageHistory = stageHistoryRepository.save(stageHistory);

        return mapToResponse(savedStageHistory);
    }

    @Override
    public StageHistoryResponseDTO getStageHistoryById(Long id) {

        StageHistory stageHistory = stageHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stage history not found"));

        return mapToResponse(stageHistory);
    }

    @Override
    public List<StageHistoryResponseDTO> getAllStageHistories() {

        return stageHistoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StageHistoryResponseDTO updateStageHistory(Long id, StageHistoryRequestDTO requestDTO) {

        StageHistory stageHistory = stageHistoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stage history not found"));

        Application application = applicationRepository.findById(requestDTO.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        PipelineStage fromStage = pipelineStageRepository.findById(requestDTO.getFromStageId())
                .orElseThrow(() -> new ResourceNotFoundException("From stage not found"));

        PipelineStage toStage = pipelineStageRepository.findById(requestDTO.getToStageId())
                .orElseThrow(() -> new ResourceNotFoundException("To stage not found"));

        User changedBy = userRepository.findById(requestDTO.getChangedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        stageHistory.setApplication(application);
        stageHistory.setFromStage(fromStage);
        stageHistory.setToStage(toStage);
        stageHistory.setChangedBy(changedBy);
        stageHistory.setNotes(requestDTO.getNotes());

        StageHistory updatedStageHistory = stageHistoryRepository.save(stageHistory);

        return mapToResponse(updatedStageHistory);
    }

    @Override
    public void deleteStageHistory(Long id) {

        stageHistoryRepository.deleteById(id);
    }

    private StageHistoryResponseDTO mapToResponse(StageHistory stageHistory) {

        StageHistoryResponseDTO responseDTO = new StageHistoryResponseDTO();

        responseDTO.setId(stageHistory.getId());
        responseDTO.setApplicationId(stageHistory.getApplication().getId());
        responseDTO.setFromStageId(stageHistory.getFromStage().getId());
        responseDTO.setToStageId(stageHistory.getToStage().getId());
        responseDTO.setChangedById(stageHistory.getChangedBy().getId());
        responseDTO.setChangedAt(stageHistory.getChangedAt());
        responseDTO.setNotes(stageHistory.getNotes());

        return responseDTO;
    }
    
    @Override
    public List<StageHistoryResponseDTO> getStageHistoryByApplicationId(Long applicationId) {

        return stageHistoryRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}