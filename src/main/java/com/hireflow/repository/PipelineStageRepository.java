package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.PipelineStage;
import com.hireflow.enums.StageType;

public interface PipelineStageRepository extends JpaRepository<PipelineStage,Long>{

	
	List<PipelineStage> findByCompanyId(Long companyId);

	List<PipelineStage> findByStageType(StageType stageType);
}
