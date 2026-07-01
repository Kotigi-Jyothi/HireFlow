package com.hireflow.dto.response;

import com.hireflow.enums.ApplicationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationStatusCountResponseDTO {

    private ApplicationStatus status;

    private Long count;
}