package com.hireflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.response.ApplicationStatusCountResponseDTO;
import com.hireflow.dto.response.DashboardResponseDTO;
import com.hireflow.enums.ApplicationStatus;
import com.hireflow.repository.ApplicationRepository;
import com.hireflow.repository.InterviewRepository;
import com.hireflow.repository.JobRepository;
import com.hireflow.repository.OfferLetterRepository;
import com.hireflow.repository.UserRepository;
import com.hireflow.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

	
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private InterviewRepository interviewRepository;

	@Autowired
	private OfferLetterRepository offerLetterRepository;

	@Override
	public DashboardResponseDTO getDashboardSummary() {

	    DashboardResponseDTO responseDTO = new DashboardResponseDTO();

	    responseDTO.setTotalJobs(jobRepository.count());

	    responseDTO.setTotalCandidates(userRepository.count());

	    responseDTO.setTotalApplications(applicationRepository.count());

	    responseDTO.setTotalInterviews(interviewRepository.count());

	    responseDTO.setTotalOfferLetters(offerLetterRepository.count());

	    return responseDTO;
	}
	
	@Override
	public List<ApplicationStatusCountResponseDTO> getApplicationStatusSummary() {

	    List<Object[]> results = applicationRepository.countApplicationsByStatus();

	    List<ApplicationStatusCountResponseDTO> responseList = new ArrayList<>();

	    for (Object[] row : results) {

	        ApplicationStatusCountResponseDTO dto =
	                new ApplicationStatusCountResponseDTO();

	        dto.setStatus((ApplicationStatus) row[0]);
	        dto.setCount((Long) row[1]);

	        responseList.add(dto);
	    }

	    return responseList;
	}
}
