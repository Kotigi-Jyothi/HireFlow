package com.hireflow.service.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.hireflow.dto.websocket.DraftScoreDTO;
import com.hireflow.service.DraftScoreMemoryService;

@Service
public class DraftScoreMemoryServiceImpl
        implements DraftScoreMemoryService {

    private final ConcurrentHashMap<Long, DraftScoreDTO> drafts =
            new ConcurrentHashMap<>(); //is your RAM.

    @Override
    public void saveDraft(DraftScoreDTO draft) {

        drafts.put(draft.getInterviewId(), draft);
    }

    @Override
    public DraftScoreDTO getDraft(Long interviewId) {

        return drafts.get(interviewId);
    }

    @Override
    public void removeDraft(Long interviewId) {

        drafts.remove(interviewId);
    }

}