package com.hireflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireflow.dto.response.ApplicationStatusCountResponseDTO;
import com.hireflow.dto.response.DashboardResponseDTO;
import com.hireflow.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardResponseDTO getDashboardSummary() {

        return dashboardService.getDashboardSummary();
    }

    @GetMapping("/application-status")
    public List<ApplicationStatusCountResponseDTO>
    getApplicationStatusSummary() {

        return dashboardService.getApplicationStatusSummary();
    }
}