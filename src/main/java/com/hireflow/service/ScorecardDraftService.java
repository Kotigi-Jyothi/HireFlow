package com.hireflow.service;

import com.hireflow.dto.websocket.DraftScoreDTO;

public interface ScorecardDraftService {

    void broadcastDraft(DraftScoreDTO draftScoreDTO);

}