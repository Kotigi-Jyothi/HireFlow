package com.hireflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireflow.dto.response.AnalyticsFunnelResponseDTO;
import com.hireflow.service.AnalyticsService;

@RestController
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/api/analytics/funnel")
    public AnalyticsFunnelResponseDTO getHiringFunnelAnalytics() {

        return analyticsService.getHiringFunnelAnalytics();
    }
}