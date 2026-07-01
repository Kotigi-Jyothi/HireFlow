package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hireflow.entity.Application;
import com.hireflow.enums.ApplicationStatus;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

	//Search/Filter APIs (Custom Repository Methods)
	List<Application> findByStatus(ApplicationStatus status);

	List<Application> findByCandidateId(Long candidateId);

	List<Application> findByJobId(Long jobId);

	@Query("""
		       SELECT a.status, COUNT(a)
		       FROM Application a
		       GROUP BY a.status
		       """)
		List<Object[]> countApplicationsByStatus();
	
		@Query(value = """
			    SELECT
			        a.id,
			        a.candidate_id,
			        a.overall_score,
			        RANK() OVER (ORDER BY a.overall_score DESC) AS candidate_rank
			    FROM applications a
			    WHERE a.job_id = :jobId
			    ORDER BY a.overall_score DESC
			    """, nativeQuery = true)
			List<Object[]> rankCandidatesByJob(@Param("jobId") Long jobId);
	
}
