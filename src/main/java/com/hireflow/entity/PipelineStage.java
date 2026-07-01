package com.hireflow.entity;

import java.util.ArrayList;
import java.util.List;

import com.hireflow.enums.StageType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pipeline_stages")
@Getter
@Setter
public class PipelineStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stageName;

    private Integer sequenceOrder;

    @Enumerated(EnumType.STRING)
    private StageType stageType;

    private Integer slaDays; //SLA = Service Level Agreement
    						//It tells how many days are allowed to complete this stage.

    @Lob //LONGTEXT
    private String autoEmailTemplate;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    
    @OneToMany(mappedBy = "currentStage")
    private List<Application> applications = new ArrayList<>();
}