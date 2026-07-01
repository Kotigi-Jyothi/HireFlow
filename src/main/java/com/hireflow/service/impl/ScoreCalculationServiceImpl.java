package com.hireflow.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hireflow.entity.Application;
import com.hireflow.entity.ScorecardSubmission;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.ScorecardSubmissionRepository;
import com.hireflow.service.RankingWebSocketService;
import com.hireflow.service.ScoreCalculationService;

@Service
public class ScoreCalculationServiceImpl implements ScoreCalculationService {

    @Autowired
    private ScorecardSubmissionRepository scorecardSubmissionRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private RankingWebSocketService rankingWebSocketService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void recalculateScorecard(Long scorecardSubmissionId) {

        ScorecardSubmission submission =
                scorecardSubmissionRepository.findById(scorecardSubmissionId)
                .orElseThrow(() ->
                        new RuntimeException("Scorecard not found"));

        try {

            Map<String, Integer> weights =
                    objectMapper.readValue(
                            submission.getScorecardTemplate().getCriteria(),
                            new TypeReference<Map<String, Integer>>() {});

            Map<String, Integer> scores =
                    objectMapper.readValue(
                            submission.getScores(),
                            new TypeReference<Map<String, Integer>>() {});

            double weightedSum = 0;
            int totalWeight = 0;

            for (String criterion : weights.keySet()) {

                Integer weight = weights.get(criterion);
                Integer score = scores.get(criterion);

                if (score != null) {

                    weightedSum += score * weight;
                    totalWeight += weight;
                }
            }

            double overallScore = 0;

            if (totalWeight > 0) {
                overallScore = weightedSum / totalWeight;
            }

            submission.setOverallRating(overallScore);

            scorecardSubmissionRepository.save(submission);

            recalculateApplication(
                    submission.getInterview()
                            .getApplication()
                            .getId());

            System.out.println("Calculated Score : " + overallScore);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error calculating score : " + e.getMessage());
        }
    }

    @Override
    public void recalculateApplication(Long applicationId) {

        Application application =
                applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        List<ScorecardSubmission> submissions =
                scorecardSubmissionRepository
                        .findByInterviewApplicationId(applicationId);

        if (submissions.isEmpty()) {

            application.setOverallScore(0.0);

        } else {

            double total = 0;

            for (ScorecardSubmission s : submissions) {

                total += s.getOverallRating();
            }

            application.setOverallScore(
                    total / submissions.size());
        }

        applicationRepository.save(application);
        
        rankingWebSocketService.broadcastRanking(
                application.getJob().getId());

        System.out.println(
                "Application Overall Score : "
                        + application.getOverallScore());
    }
}