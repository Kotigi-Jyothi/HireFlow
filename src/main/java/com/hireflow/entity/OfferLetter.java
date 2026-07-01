package com.hireflow.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hireflow.enums.OfferStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "offer_letters")
@Getter
@Setter
public class OfferLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String pdfPath;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    private String signatureToken;

    private LocalDateTime sentAt;

    private BigDecimal salary;

    private LocalDate joiningDate;

    private LocalDateTime signedAt;
    
    @Column(name = "signed_ip")
    private String signedIp;

    @OneToOne
    @JoinColumn(name = "application_id")
    private Application application;
}