package com.eventhub.rfps.entity;

import com.eventhub.auth.entity.User;
import com.eventhub.venues.entity.Venue;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "rfps")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rfp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "id", nullable = false)
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = false)
    private User buyer;

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

    @Setter
    @Column(nullable = false)
    private String status; // e.g., submitted, accepted, countered, declined

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column
    private String idempotencyKey;
}

