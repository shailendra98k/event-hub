package com.eventhub.reviews.repository;

import com.eventhub.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVenueId(Long venueId);
    List<Review> findByBuyerUserId(Long buyerUserId);
    // Add more query methods as needed
}

