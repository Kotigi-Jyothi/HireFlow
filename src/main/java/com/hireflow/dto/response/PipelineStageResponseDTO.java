package com.hireflow.dto.response;

import com.hireflow.enums.StageType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PipelineStageResponseDTO {

	private Long id;
	private String stageName;
	private Integer sequenceOrder;
	private StageType stageType;
	private Integer slaDays;
	private String autoEmailTemplate;
	private Long companyId;
}
