package com.hireflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StageHistoryRequestDTO {

	 @NotNull(message = "Application id is required")
	    private Long applicationId;

	    @NotNull(message = "From stage id is required")
	    private Long fromStageId;

	    @NotNull(message = "To stage id is required")
	    private Long toStageId;

	    @NotNull(message = "Changed by id is required")
	    private Long changedById;

	    @NotBlank(message = "Notes are required")
	    private String notes;
}
