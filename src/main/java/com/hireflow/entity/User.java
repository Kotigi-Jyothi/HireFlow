package com.hireflow.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.hireflow.enums.UserRole;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Boolean isActive;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "createdBy")
    private List<Job> jobsCreated = new ArrayList<>();

    @OneToMany(mappedBy = "candidate")
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "interviewer")
    private List<Interview> interviews = new ArrayList<>();

    
    @OneToMany(mappedBy = "interviewer")
    private List<ScorecardSubmission> scorecardSubmissions = new ArrayList<>();
}