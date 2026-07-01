package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.ScorecardTemplate;

public interface ScorecardTemplateRepository extends JpaRepository<ScorecardTemplate,Long> {

	List<ScorecardTemplate> findByCompanyId(Long companyId);
	
}
