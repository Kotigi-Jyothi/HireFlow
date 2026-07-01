package com.hireflow.dto.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlaBreachDTO {

    private Long applicationId;

    private String candidateName;

    private String stageName;

    private String message;
}