package com.eventhub.reviews.service;

import com.eventhub.auth.entity.User;
import com.eventhub.auth.repository.UserRepository;
import com.eventhub.reviews.dto.ReviewRequest;
import com.eventhub.reviews.entity.Review;
import com.eventhub.reviews.repository.ReviewRepository;
import com.eventhub.rfps.entity.Rfp;
import com.eventhub.rfps.repository.RfpRepository;
import com.eventhub.venueDiscovery.entity.Venue;
import com.eventhub.venueDiscovery.repository.VenueRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private RfpRepository rfpRepository;

    public Review submitReview(ReviewRequest request) throws AuthenticationException, BadRequestException {
        // Get buyerId from security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails userDetails)) {
            throw new AuthenticationException("User not authenticated");
        }
        Optional<User> buyerOpt = userRepository.findByEmail(userDetails.getUsername());
        Optional<Venue> venueOpt = venueRepository.findById(request.venueId);


        // user should be able to submit review only if they have booked the venue before
        // there should be one rfp in completed status for this user and venue

        List<Rfp> completedRfp = rfpRepository.findByBuyerIdAndVenueIdAndStatus(buyerOpt.get().getId(), request.venueId, "COMPLETED");
        if (completedRfp.isEmpty()) {
            throw new BadRequestException("User has not booked this venue before");
        }

        if (buyerOpt.isEmpty() || venueOpt.isEmpty()) {
            throw new BadRequestException("Invalid buyer or venue");
        }

        User buyer = buyerOpt.get();
        String rawKey = buyer.getId() + ":" + request.venueId + ":" + request.review + ":" + request.rating;
        String idempotencyKey;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawKey.getBytes(StandardCharsets.UTF_8));
            idempotencyKey = java.util.Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generating idempotency key");
        }
        Optional<Review> existing = reviewRepository.findByIdempotencyKey(idempotencyKey);
        if (existing.isPresent()) {
            return existing.get();
        }
        Review review = Review.builder()
                .venue(venueOpt.get())
                .buyer(buyer)
                .rating(request.rating)
                .review(request.review)
                .createdAt(Instant.now())
                .idempotencyKey(idempotencyKey)
                .build();
        reviewRepository.save(review);
        return review;
    }
}

