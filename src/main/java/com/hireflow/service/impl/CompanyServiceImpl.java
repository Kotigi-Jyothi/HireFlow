package com.hireflow.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.CompanyRequestDTO;
import com.hireflow.dto.response.CompanyResponseDTO;
import com.hireflow.entity.Company;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.CompanyRepository;
import com.hireflow.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompanyResponseDTO createCompany(CompanyRequestDTO requestDTO) {

        Company company = new Company();

        company.setName(requestDTO.getName());
        company.setIndustry(requestDTO.getIndustry());
        company.setSize(requestDTO.getSize());
        company.setLogoPath(requestDTO.getLogoPath());

        Company savedCompany = companyRepository.save(company);

        return mapToResponse(savedCompany);
    }

    @Override
    public CompanyResponseDTO getCompanyById(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        return mapToResponse(company);
    }

    @Override
    public List<CompanyResponseDTO> getAllCompanies() {

        return companyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponseDTO updateCompany(Long id, CompanyRequestDTO requestDTO) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        company.setName(requestDTO.getName());
        company.setIndustry(requestDTO.getIndustry());
        company.setSize(requestDTO.getSize());
        company.setLogoPath(requestDTO.getLogoPath());

        Company updatedCompany = companyRepository.save(company);

        return mapToResponse(updatedCompany);
    }

    @Override
    public void deleteCompany(Long id) {

        companyRepository.deleteById(id);
    }

    private CompanyResponseDTO mapToResponse(Company company) {

        CompanyResponseDTO responseDTO = new CompanyResponseDTO();

        responseDTO.setId(company.getId());
        responseDTO.setName(company.getName());
        responseDTO.setIndustry(company.getIndustry());
        responseDTO.setSize(company.getSize());
        responseDTO.setLogoPath(company.getLogoPath());

        return responseDTO;
    }
    
    @Override
    public List<CompanyResponseDTO> getCompaniesByIndustry(String industry) {

        return companyRepository.findByIndustry(industry)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}