package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.ScorecardSubmission;

public interface ScorecardSubmissionRepository extends JpaRepository<ScorecardSubmission,Long> {

	List<ScorecardSubmission> findByInterviewId(Long interviewId);

	List<ScorecardSubmission> findByInterviewerId(Long interviewerId);
	
	boolean existsByInterviewId(Long interviewId);
	
	List<ScorecardSubmission> findByInterviewApplicationId(Long applicationId);
	
	boolean existsByInterviewIdAndInterviewerId(Long interviewId, Long interviewerId);

	void deleteById(Long id);
	
	
	List<ScorecardSubmission> findByScorecardTemplateId(Long templateId);
	
	
}
