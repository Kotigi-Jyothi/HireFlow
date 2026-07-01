package com.hireflow.dto.response;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyticsFunnelResponseDTO {

    private Map<String, Double> conversionRates;

    private Map<String, Double> averageTimeInStage;

    private Double scorecardSubmissionRate;

    private Double offerAcceptanceRate;

}