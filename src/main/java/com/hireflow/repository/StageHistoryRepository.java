package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.StageHistory;

public interface StageHistoryRepository extends JpaRepository<StageHistory,Long> {

	List<StageHistory> findByApplicationId(Long applicationId);
}
