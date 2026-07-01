package com.hireflow.service.impl;

import org.springframework.stereotype.Service;

import com.hireflow.entity.PipelineStage;
import com.hireflow.service.PipelineStateMachine;

@Service
public class PipelineStateMachineImpl
        implements PipelineStateMachine {

    @Override
    public boolean canMove(PipelineStage currentStage,
                           PipelineStage nextStage) {

        return nextStage.getSequenceOrder()
                == currentStage.getSequenceOrder() + 1;
    }
}