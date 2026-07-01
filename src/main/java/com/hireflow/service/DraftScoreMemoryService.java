package com.hireflow.service;

import com.hireflow.dto.websocket.DraftScoreDTO;

public interface DraftScoreMemoryService {

    void saveDraft(DraftScoreDTO draft);

    DraftScoreDTO getDraft(Long interviewId);

    void removeDraft(Long interviewId);

}