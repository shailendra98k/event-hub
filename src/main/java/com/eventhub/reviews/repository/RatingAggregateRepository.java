package com.eventhub.reviews.repository;

import com.eventhub.reviews.entity.RatingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingAggregateRepository extends JpaRepository<RatingAggregate, Long> {
    RatingAggregate findByVenueId(Long venueId);
}
