package com.eventhub.reviews.repository;

import com.eventhub.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByIdempotencyKey(String idempotencyKey);
}
