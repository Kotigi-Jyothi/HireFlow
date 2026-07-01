package com.hireflow.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hireflow.enums.InterviewStatus;
import com.hireflow.enums.InterviewType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "interviews")
@Getter
@Setter
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime scheduledAt;

    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "interviewer_id")
    private User interviewer;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private PipelineStage stage;
    
    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
    private List<ScorecardSubmission> scorecardSubmissions = new ArrayList<>();
}