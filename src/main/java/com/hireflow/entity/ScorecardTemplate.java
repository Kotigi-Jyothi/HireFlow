package com.hireflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//evaluation format

@Entity
@Table(name = "scorecard_templates")
@Getter
@Setter
public class ScorecardTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //eg : Java Backend Template

    @Column(columnDefinition = "json")
    private String criteria; 	//subject:marks

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}