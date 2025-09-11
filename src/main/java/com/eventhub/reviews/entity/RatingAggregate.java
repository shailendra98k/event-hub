package com.eventhub.reviews.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rating_aggregates")
public class RatingAggregate {
    @Id
    private Long venueId;

    @Column(nullable = false)
    private Double avgRating;

    @Column(nullable = false)
    private Integer countTotal;

    @Column(length = 1000)
    private String countsJson; // JSON string for rating counts

    // Getters and setters
}
