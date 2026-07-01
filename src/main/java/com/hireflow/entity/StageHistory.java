package com.hireflow.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stage_history")
@Getter
@Setter
public class StageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime changedAt;

    @Column(length = 1000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "from_stage")
    private PipelineStage fromStage;

    @ManyToOne
    @JoinColumn(name = "to_stage")
    private PipelineStage toStage;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    private User changedBy;
}