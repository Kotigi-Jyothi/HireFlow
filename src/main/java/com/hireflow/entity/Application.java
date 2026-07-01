package com.hireflow.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hireflow.enums.ApplicationStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "applications")
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appliedAt;

    private String resumePath;

    private Double overallScore;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime lastStageChangedAt;
    
    @Column(nullable = false)
    private Boolean slaBreached = false;
   
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private User candidate;

    @ManyToOne
    @JoinColumn(name = "current_stage_id")
    private PipelineStage currentStage;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<StageHistory> stageHistories = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<Interview> interviews = new ArrayList<>();

    
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<OfferLetter> offerLetters = new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<EmailLog> emailLogs = new ArrayList<>();

}