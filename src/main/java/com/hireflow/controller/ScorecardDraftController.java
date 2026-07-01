package com.hireflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hireflow.dto.websocket.DraftScoreDTO;
import com.hireflow.service.DraftScoreMemoryService;
import com.hireflow.service.ScorecardDraftService;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/api/scorecard-drafts")
public class ScorecardDraftController {

    @Autowired
    private ScorecardDraftService scorecardDraftService;

    @Autowired
    private DraftScoreMemoryService draftScoreMemoryService;
    
    @MessageMapping("/scorecard/draft")
    public void sendDraftScore(DraftScoreDTO draftScoreDTO) {

        scorecardDraftService.broadcastDraft(draftScoreDTO);
    }
    
    
    @GetMapping("/draft/{interviewId}")
    @ResponseBody
    public DraftScoreDTO getDraftScore(
            @PathVariable Long interviewId) {

        return draftScoreMemoryService.getDraft(interviewId);
    }
    
    @PostMapping("/draft")
    @ResponseBody
    public String saveDraft(@RequestBody DraftScoreDTO draftScoreDTO) {

        draftScoreMemoryService.saveDraft(draftScoreDTO);

        return "Draft saved in memory.";
    }
}