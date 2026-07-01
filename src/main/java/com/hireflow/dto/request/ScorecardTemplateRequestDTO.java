package com.hireflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScorecardTemplateRequestDTO {

	 @NotBlank(message = "Template name is required")
	    private String name;

	    @NotBlank(message = "Criteria is required")
	    private String criteria;

	    @NotNull(message = "Company id is required")
	    private Long companyId;
}
