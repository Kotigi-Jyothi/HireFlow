package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hireflow.entity.Application;

@Repository
public interface AnalyticsRepository
        extends JpaRepository<Application, Long> {

	//calculate offer acceptance rate 
	//eg: 10 offers sent out 6 accepted then it wil be 6/10 * 100 = 60%
	@Query(value = """
			SELECT
			ROUND(
			(
			COUNT(CASE WHEN status='ACCEPTED' THEN 1 END)
			*100.0
			)
			/
			NULLIF(COUNT(*),0),
			2
			)
			FROM offer_letters
			""", nativeQuery = true)
			Double getOfferAcceptanceRate();
	
	//calculates scorecard submission rate
	//10 interviews conducted but only 7 submitted scorecard, rate  will be 70%
	
	@Query(value = """
			SELECT
			ROUND(
			(
			(SELECT COUNT(*) FROM scorecard_submissions) * 100.0
			)
			/
			NULLIF((SELECT COUNT(*) FROM interviews),0),
			2
			)
			""", nativeQuery = true)
			Double getScorecardSubmissionRate();
	

	//avg time
	
	@Query(value = """
			WITH ordered_history AS (

			    SELECT
			        sh.application_id,
			        ps.stage_name,
			        sh.changed_at,
			        LEAD(sh.changed_at)
			            OVER (
			                PARTITION BY sh.application_id
			                ORDER BY sh.changed_at
			            ) AS next_changed_at

			    FROM stage_history sh
			    JOIN pipeline_stages ps
			      ON sh.to_stage = ps.id
			),

			applied_stage AS (

			    SELECT
			        a.id AS application_id,
			        'Applied' AS stage_name,
			        a.applied_at AS start_time,

			        (
			            SELECT MIN(changed_at)
			            FROM stage_history sh
			            WHERE sh.application_id = a.id
			        ) AS end_time

			    FROM applications a
			),

			all_stage_times AS (

			    SELECT
			        stage_name,
			        TIMESTAMPDIFF(
			            HOUR,
			            start_time,
			            end_time
			        ) AS hours_spent
			    FROM applied_stage

			    UNION ALL

			    SELECT
			        stage_name,
			        TIMESTAMPDIFF(
			            HOUR,
			            changed_at,
			            COALESCE(next_changed_at, NOW())
			        )
			    FROM ordered_history

			)

			SELECT
			    stage_name,
			    ROUND(AVG(hours_spent),2)
			FROM all_stage_times
			WHERE hours_spent IS NOT NULL
			GROUP BY stage_name
			ORDER BY FIELD(
			    stage_name,
			    'Applied',
			    'Screening',
			    'Technical',
			    'HR Round',
			    'Offer',
			    'Hired'
			)
			""", nativeQuery = true)
			List<Object[]> getAverageTimeInStage();
			
			
			
			//avg conversions
			
			@Query(value = """
					WITH stage_counts AS
					(
					    SELECT
					        ps.id,
					        ps.stage_name,
					        ps.sequence_order,

					        CASE
					            WHEN ps.stage_type = 'APPLIED'
					            THEN (SELECT COUNT(*) FROM applications)

					            ELSE COUNT(sh.to_stage)
					        END AS candidate_count

					    FROM pipeline_stages ps

					    LEFT JOIN stage_history sh
					           ON sh.to_stage = ps.id

					    GROUP BY
					        ps.id,
					        ps.stage_name,
					        ps.sequence_order,
					        ps.stage_type
					)

					SELECT

					    CONCAT(prev.stage_name,'->',curr.stage_name) AS conversion_stage,

					    ROUND(
					        (curr.candidate_count * 100.0)
					        /
					        NULLIF(prev.candidate_count,0),
					        2
					    ) AS conversion_rate

					FROM stage_counts curr

					JOIN stage_counts prev
					ON curr.sequence_order = prev.sequence_order + 1

					ORDER BY curr.sequence_order
					""", nativeQuery = true)
					List<Object[]> getConversionRates();
}




