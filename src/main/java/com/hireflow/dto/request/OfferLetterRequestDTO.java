package com.hireflow.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.hireflow.enums.OfferStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferLetterRequestDTO {

    @NotNull(message = "Application id is required")
    private Long applicationId;

    @NotBlank(message = "Content is required")
    private String content;

    private String pdfPath;

    private String signatureToken;

    @NotNull(message = "Offer status is required")
    private OfferStatus status;
    
    @NotNull(message = "Salary is required")
    private BigDecimal salary;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;
}
