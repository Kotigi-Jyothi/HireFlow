package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.response.ApplicationStatusCountResponseDTO;
import com.hireflow.dto.response.DashboardResponseDTO;

public interface DashboardService {

    DashboardResponseDTO getDashboardSummary();

    List<ApplicationStatusCountResponseDTO> getApplicationStatusSummary();
}