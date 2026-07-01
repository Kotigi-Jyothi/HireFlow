package com.hireflow.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity  //The email_log table keeps a record of every email sent to a candidate.
@Table(name = "email_log")
@Getter
@Setter
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;

    private String subject;

    private String templateUsed;

    private LocalDateTime sentAt;

    private String status;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;
}