package com.hireflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.hireflow.dto.response.CandidateRankingResponseDTO;
import com.hireflow.service.ApplicationService;
import com.hireflow.service.RankingWebSocketService;

@Service
public class RankingWebSocketServiceImpl implements RankingWebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ApplicationService applicationService;

    @Override
    public void broadcastRanking(Long jobId) {

        List<CandidateRankingResponseDTO> rankings =
                applicationService.getCandidateRanking(jobId);

        messagingTemplate.convertAndSend(
                "/topic/job/" + jobId + "/rankings",
                rankings);
    }

}