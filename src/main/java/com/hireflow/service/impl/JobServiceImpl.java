package com.hireflow.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.JobRequestDTO;
import com.hireflow.dto.response.JobResponseDTO;
import com.hireflow.entity.Company;
import com.hireflow.entity.Job;
import com.hireflow.entity.User;
import com.hireflow.enums.JobStatus;
import com.hireflow.enums.UserRole;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.CompanyRepository;
import com.hireflow.repository.JobRepository;
import com.hireflow.repository.UserRepository;
import com.hireflow.service.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public JobResponseDTO createJob(JobRequestDTO requestDTO) {

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        User createdBy = userRepository.findById(requestDTO.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        //only admin/hr can create the jobs
        //Throw an exception only if the role is neither HR_MANAGER nor ADMIN.
        if (createdBy.getRole() != UserRole.HR_MANAGER &&
            createdBy.getRole() != UserRole.ADMIN) {

            throw new RuntimeException(
                    "Only HR_MANAGER or ADMIN can create jobs");
        }

        Job job = new Job();

        job.setTitle(requestDTO.getTitle());
        job.setDepartment(requestDTO.getDepartment());
        job.setLocation(requestDTO.getLocation());
        job.setType(requestDTO.getType());
        job.setExperienceRequired(requestDTO.getExperienceRequired());
        job.setSkillsRequired(requestDTO.getSkillsRequired());
        job.setSalaryMin(requestDTO.getSalaryMin());
        job.setSalaryMax(requestDTO.getSalaryMax());
        job.setOpeningsCount(requestDTO.getOpeningsCount());
        job.setStatus(requestDTO.getStatus());
        job.setCompany(company);
        job.setCreatedBy(createdBy);

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    @Override
    public JobResponseDTO getJobById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        return mapToResponse(job);
    }

    @Override
    public List<JobResponseDTO> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobResponseDTO updateJob(Long id, JobRequestDTO requestDTO) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        User createdBy = userRepository.findById(requestDTO.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        job.setTitle(requestDTO.getTitle());
        job.setDepartment(requestDTO.getDepartment());
        job.setLocation(requestDTO.getLocation());
        job.setType(requestDTO.getType());
        job.setExperienceRequired(requestDTO.getExperienceRequired());
        job.setSkillsRequired(requestDTO.getSkillsRequired());
        job.setSalaryMin(requestDTO.getSalaryMin());
        job.setSalaryMax(requestDTO.getSalaryMax());
        job.setOpeningsCount(requestDTO.getOpeningsCount());
        job.setStatus(requestDTO.getStatus());
        job.setCompany(company);
        job.setCreatedBy(createdBy);

        Job updatedJob = jobRepository.save(job);

        return mapToResponse(updatedJob);
    }

    @Override
    public void deleteJob(Long id) {

        jobRepository.deleteById(id);
    }

    private JobResponseDTO mapToResponse(Job job) {

        JobResponseDTO responseDTO = new JobResponseDTO();

        responseDTO.setId(job.getId());
        responseDTO.setTitle(job.getTitle());
        responseDTO.setDepartment(job.getDepartment());
        responseDTO.setLocation(job.getLocation());
        responseDTO.setType(job.getType());
        responseDTO.setExperienceRequired(job.getExperienceRequired());
        responseDTO.setSkillsRequired(job.getSkillsRequired());
        responseDTO.setSalaryMin(job.getSalaryMin());
        responseDTO.setSalaryMax(job.getSalaryMax());
        responseDTO.setOpeningsCount(job.getOpeningsCount());
        responseDTO.setStatus(job.getStatus());
        responseDTO.setCompanyId(job.getCompany().getId());
        responseDTO.setCreatedById(job.getCreatedBy().getId());

        return responseDTO;
    }
    @Override
    public List<JobResponseDTO> getJobsByCompanyId(Long companyId) {

        return jobRepository.findByCompanyId(companyId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
  
    @Override
    public List<JobResponseDTO> getJobsByStatus(JobStatus status) {

        return jobRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public List<JobResponseDTO> searchJobsByTitle(String title) {

        return jobRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}