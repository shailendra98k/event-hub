package com.eventhub.venueDiscovery.entity;

import com.eventhub.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "venues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(nullable = false)
    private Integer rate;

    @Column(nullable = false)
    private String address;

    @ElementCollection
    @CollectionTable(name = "venue_tags", joinColumns = @JoinColumn(name = "venue_id"))
    @Column(name = "tag")
    private List<String> tags;

    @ManyToOne
    @JoinColumn(name = "owner_user_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private Instant createdAt;
}
