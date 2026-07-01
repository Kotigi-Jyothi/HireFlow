package com.hireflow.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hireflow.dto.request.UserRequestDTO;
import com.hireflow.dto.response.UserResponseDTO;
import com.hireflow.entity.Company;
import com.hireflow.entity.User;
import com.hireflow.enums.UserRole;
import com.hireflow.exception.ResourceNotFoundException;
import com.hireflow.repository.CompanyRepository;
import com.hireflow.repository.UserRepository;
import com.hireflow.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        User user = new User();

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setRole(requestDTO.getRole());
        user.setIsActive(requestDTO.getIsActive());
        user.setCreatedAt(LocalDateTime.now());
        user.setCompany(company);

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Company company = companyRepository.findById(requestDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setRole(requestDTO.getRole());
        user.setIsActive(requestDTO.getIsActive());
        user.setCompany(company);

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    private UserResponseDTO mapToResponse(User user) {

        UserResponseDTO responseDTO = new UserResponseDTO();

        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setIsActive(user.getIsActive());
        responseDTO.setCreatedAt(user.getCreatedAt());
        responseDTO.setCompanyId(user.getCompany().getId());

        return responseDTO;
    }
    
    @Override
    public List<UserResponseDTO> getUsersByRole(UserRole role) {

        return userRepository.findByRole(role)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public List<UserResponseDTO> searchUsersByName(String name) {

        return userRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}