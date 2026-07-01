package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.Job;
import com.hireflow.enums.JobStatus;

public interface JobRepository extends JpaRepository<Job,Long> {

	
	List<Job> findByCompanyId(Long companyId);

	List<Job> findByStatus(JobStatus status);
	
	List<Job> findByTitleContainingIgnoreCase(String title);

}
