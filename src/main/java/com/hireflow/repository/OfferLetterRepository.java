package com.hireflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hireflow.entity.OfferLetter;

public interface OfferLetterRepository extends JpaRepository<OfferLetter,Long>{

	Optional<OfferLetter> findByApplicationId(Long applicationId);
	
	Optional<OfferLetter> findBySignatureToken(
	        String signatureToken);
}
