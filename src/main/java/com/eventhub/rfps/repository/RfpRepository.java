package com.eventhub.rfps.repository;

import com.eventhub.rfps.entity.Rfp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RfpRepository extends JpaRepository<Rfp, Long> {
    List<Rfp> findByVenueId(Long venueId);
    List<Rfp> findByBuyerUserId(Long buyerUserId);
    // Add more query methods as needed
}

