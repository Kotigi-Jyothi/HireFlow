package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Long>{


	List<Company> findByIndustry(String industry);
}
