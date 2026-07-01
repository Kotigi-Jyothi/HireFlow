package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.Interview;
import com.hireflow.enums.InterviewStatus;

public interface InterviewRepository extends JpaRepository<Interview,Long> {

	List<Interview> findByInterviewerId(Long interviewerId);

	List<Interview> findByStatus(InterviewStatus status);

	List<Interview> findByApplicationId(Long applicationId);
	
	List<Interview> findByApplicationIdAndStageId(
	        Long applicationId,
	        Long stageId);
	
	List<Interview> findByApplicationCandidateId(
	        Long candidateId);
}
