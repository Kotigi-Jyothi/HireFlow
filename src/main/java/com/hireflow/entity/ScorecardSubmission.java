package com.hireflow.entity;

import java.time.LocalDateTime;

import com.hireflow.enums.Recommendation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scorecard_submissions")
@Getter
@Setter
public class ScorecardSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "json")
    private String scores;

    private Double overallRating;

    @Enumerated(EnumType.STRING)
    private Recommendation recommendation;

    @Lob
    private String strengths;

    @Lob
    private String weaknesses;

    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "interview_id") //Which interview this feedback belongs to eg: techincal
    private Interview interview;

    @ManyToOne
    @JoinColumn(name = "interviewer_id")
    private User interviewer; //how submited this form/ who took interview


    @ManyToOne
    @JoinColumn(name = "template_id")
    private ScorecardTemplate scorecardTemplate;
}