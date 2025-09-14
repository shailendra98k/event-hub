package com.eventhub.reviews.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long venueId;

    @Column(nullable = false)
    private Long buyerUserId;

    @Column(nullable = false)
    private Integer rating;

    @Column(length = 1000)
    private String text;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private String idempotencyKey;

    // Getters and setters
}

