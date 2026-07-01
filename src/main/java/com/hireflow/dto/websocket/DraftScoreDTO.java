package com.hireflow.dto.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DraftScoreDTO {

    private Long interviewId;

    private Long interviewerId;

    private String scores;

    private Double overallRating;
}