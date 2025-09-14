package com.eventhub.venueDiscovery.repository;

import com.eventhub.venueDiscovery.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long>, JpaSpecificationExecutor<Venue> {
    List<Venue> findByCity(String city);

    @Query("SELECT v FROM Venue v WHERE v.owner.id = :userId")
    List<Venue> findByUserId(@Param("userId") Long userId);
}
