package com.hireflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.User;
import com.hireflow.enums.UserRole;

public interface UserRepository extends JpaRepository<User,Long> {

	List<User> findByRole(UserRole role);
	
	List<User> findByNameContainingIgnoreCase(String name);
}
