package com.eventhub.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;


import java.time.Instant;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private String role; // buyer, venueOwner, admin

    @Column(nullable = false)
    private Instant createdAt;

    // Getters and setters
}

