package com.hireflow.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StageHistoryResponseDTO {

	private Long id;
	private Long applicationId;
	private Long fromStageId;
	private Long toStageId;
	private Long changedById;
	private LocalDateTime changedAt;
	private String notes;
}
