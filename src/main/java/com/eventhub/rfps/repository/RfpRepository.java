package com.eventhub.rfps.repository;

import com.eventhub.rfps.entity.Rfp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RfpRepository extends JpaRepository<Rfp, Long> {
    Page<Rfp> findByBuyerId(Long buyerId, Pageable pageable);
    Page<Rfp> findByVenueIdIn(List<Long> venueIds, Pageable pageable);
    // Add more query methods as needed
}

