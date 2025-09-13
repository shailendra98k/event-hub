package com.eventhub.rfps.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "rfps")
@Getter
@Builder
public class Rfp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long venueId;

    @Column(nullable = false)
    private Long buyerUserId;

    @Column(nullable = false)
    private Instant eventDate;

    @Column(nullable = false)
    private Integer headcount;

    @Column(nullable = false)
    private Integer budgetMin;

    @Column(nullable = false)
    private Integer budgetMax;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private String status; // e.g., submitted, accepted, countered, declined

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
}

