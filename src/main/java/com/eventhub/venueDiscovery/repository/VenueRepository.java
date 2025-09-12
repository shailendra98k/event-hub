package com.eventhub.venueDiscovery.repository;

import com.eventhub.venueDiscovery.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long>, JpaSpecificationExecutor<Venue> {
    List<Venue> findByCity(String city);
    // Add more query methods as needed for filtering
}
