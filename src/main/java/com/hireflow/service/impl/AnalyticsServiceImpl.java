package com.hireflow.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.response.AnalyticsFunnelResponseDTO;
import com.hireflow.repository.AnalyticsRepository;
import com.hireflow.service.AnalyticsService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @Override
    public AnalyticsFunnelResponseDTO getHiringFunnelAnalytics() {

        AnalyticsFunnelResponseDTO response =
                new AnalyticsFunnelResponseDTO();

        Map<String, Double> conversionRates = new LinkedHashMap<>();

        List<Object[]> rows = analyticsRepository.getConversionRates();

        for (Object[] row : rows) {

            String stage = (String) row[0];

            Double rate = row[1] != null
                    ? ((Number) row[1]).doubleValue()
                    : 0.0;

            conversionRates.put(stage, rate);
        }

        response.setConversionRates(conversionRates);

        Map<String, Double> averageTime = new LinkedHashMap<>();

        List<Object[]> stageTimeRows =
                analyticsRepository.getAverageTimeInStage();

        for (Object[] row : stageTimeRows) {

            String stage = (String) row[0];

            Double hours =
                    row[1] != null
                    ? ((Number) row[1]).doubleValue()
                    : 0.0;

            averageTime.put(stage, hours);
        }

        response.setAverageTimeInStage(averageTime); 
        

        Double submissionRate =
                analyticsRepository.getScorecardSubmissionRate();

        response.setScorecardSubmissionRate(
                submissionRate != null ? submissionRate : 0.0);

        Double offerAcceptanceRate =
                analyticsRepository.getOfferAcceptanceRate();

        response.setOfferAcceptanceRate(
                offerAcceptanceRate != null ? offerAcceptanceRate : 0.0);

        return response;
    }
}