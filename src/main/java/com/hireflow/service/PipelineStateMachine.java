package com.hireflow.service;

import com.hireflow.entity.PipelineStage;

public interface PipelineStateMachine {

    boolean canMove(PipelineStage currentStage,
                    PipelineStage nextStage);

}