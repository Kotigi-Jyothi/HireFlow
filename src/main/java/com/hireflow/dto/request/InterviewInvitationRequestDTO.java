package com.hireflow.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewInvitationRequestDTO {

    private Long applicationId;

    private Long interviewId; 
    
    private String interviewDate;

}