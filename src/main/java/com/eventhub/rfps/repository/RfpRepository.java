package com.eventhub.rfps.repository;

import com.eventhub.rfps.entity.Rfp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RfpRepository extends JpaRepository<Rfp, Long> {
    Page<Rfp> findByBuyerId(Long buyerId, Pageable pageable);
    Page<Rfp> findByVenueIdIn(List<Long> venueIds, Pageable pageable);
    List<Rfp> findByBuyerIdAndVenueIdAndStatus(Long rfpId, Long buyerId, String status);
}

