package com.hireflow.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hireflow.enums.OfferStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferLetterResponseDTO {

	private Long id;
	private Long applicationId;
	private String content;
	private String pdfPath;
	private String signatureToken;
	private OfferStatus status;
	private LocalDateTime sentAt;
	private LocalDateTime signedAt;
	private BigDecimal salary;
	private LocalDate joiningDate;
}
