package com.hireflow.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDTO {

	private Long id;
	private String name;
	private String industry;
	private String size;
	private String logoPath;
}
