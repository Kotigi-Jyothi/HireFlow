package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.EmailLog;

public interface EmailLogRepository extends JpaRepository<EmailLog,Long>{
	
	List<EmailLog> findByApplicationId(Long applicationId);
}
