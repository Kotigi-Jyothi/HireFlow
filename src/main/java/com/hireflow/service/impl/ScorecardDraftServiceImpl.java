package com.hireflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.hireflow.dto.websocket.DraftScoreDTO;
import com.hireflow.service.DraftScoreMemoryService;
import com.hireflow.service.ScorecardDraftService;

@Service
public class ScorecardDraftServiceImpl implements ScorecardDraftService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private DraftScoreMemoryService draftScoreMemoryService;
    
    @Override
    public void broadcastDraft(DraftScoreDTO draftScoreDTO) {

        // Save latest draft in memory
        draftScoreMemoryService.saveDraft(draftScoreDTO);

        // Broadcast to HR dashboard
        messagingTemplate.convertAndSend(
                "/topic/interview/"
                        + draftScoreDTO.getInterviewId()
                        + "/scorecard",
                draftScoreDTO);
    }

}



//Interviewer
//↓
//ConcurrentHashMap (RAM)
//↓
//WebSocket Broadcast