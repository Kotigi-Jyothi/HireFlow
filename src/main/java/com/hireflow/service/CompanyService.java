package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.CompanyRequestDTO;
import com.hireflow.dto.response.CompanyResponseDTO;

//service layer to use DTOs
public interface CompanyService {
    CompanyResponseDTO createCompany(CompanyRequestDTO requestDTO);

    CompanyResponseDTO getCompanyById(Long id);

    List<CompanyResponseDTO> getAllCompanies();

    CompanyResponseDTO updateCompany(Long id, CompanyRequestDTO requestDTO);

    void deleteCompany(Long id);
    
    List<CompanyResponseDTO> getCompaniesByIndustry(String industry);
}
