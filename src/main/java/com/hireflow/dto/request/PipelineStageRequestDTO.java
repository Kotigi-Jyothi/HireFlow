package com.hireflow.dto.request;

import com.hireflow.enums.StageType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PipelineStageRequestDTO {

    @NotBlank(message = "Stage name is required")
    private String stageName;

    @NotNull(message = "Sequence order is required")
    private Integer sequenceOrder;

    @NotNull(message = "Stage type is required")
    private StageType stageType;

    @NotNull(message = "SLA days are required")
    private Integer slaDays;

    private String autoEmailTemplate;

    @NotNull(message = "Company id is required")
    private Long companyId;
}
