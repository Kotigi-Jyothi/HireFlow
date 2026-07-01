package com.hireflow.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailLogResponseDTO {

	private Long id;
	private Long applicationId;
	private String recipient;
	private String subject;
	private String templateUsed;
	private LocalDateTime sentAt;
	private String status;
}
