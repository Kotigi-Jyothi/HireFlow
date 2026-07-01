package com.hireflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hireflow.dto.request.CompanyRequestDTO;
import com.hireflow.dto.response.CompanyResponseDTO;
import com.hireflow.service.CompanyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public CompanyResponseDTO createCompany(
    		@Valid @RequestBody CompanyRequestDTO requestDTO) {

        return companyService.createCompany(requestDTO);
    }

    @GetMapping("/{id}")
    public CompanyResponseDTO getCompanyById(
            @PathVariable Long id) {
    	System.out.println("inside company");
        return companyService.getCompanyById(id);
    }

    @GetMapping
    public List<CompanyResponseDTO> getAllCompanies() {

        return companyService.getAllCompanies();
    }

    @PutMapping("/{id}")
    public CompanyResponseDTO updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequestDTO requestDTO) {

        return companyService.updateCompany(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompany(id);
    }
    
    @GetMapping("/industry/{industry}")
    public List<CompanyResponseDTO> getCompaniesByIndustry(
            @PathVariable String industry) {

        return companyService.getCompaniesByIndustry(industry);
    }
}