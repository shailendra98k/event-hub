package com.eventhub.venueDiscovery.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "venues")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Integer capacityMin;

    @Column(nullable = false)
    private Integer capacityMax;

    @ElementCollection
    @CollectionTable(name = "venue_tags", joinColumns = @JoinColumn(name = "venue_id"))
    @Column(name = "tag")
    private List<String> tags;

    @Column(nullable = false)
    private Long ownerUserId;

    @Column(nullable = false)
    private Instant createdAt;

    // Getters and setters
}
