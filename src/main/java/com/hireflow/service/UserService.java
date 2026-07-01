package com.hireflow.service;

import java.util.List;

import com.hireflow.dto.request.UserRequestDTO;
import com.hireflow.dto.response.UserResponseDTO;
import com.hireflow.enums.UserRole;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO);

    void deleteUser(Long id);
    
    List<UserResponseDTO> getUsersByRole(UserRole role);

    List<UserResponseDTO> searchUsersByName(String name);
}