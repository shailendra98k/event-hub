package com.eventhub.reviews.controller;

import com.eventhub.reviews.dto.ReviewRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @PostMapping("")
    public ResponseEntity<?> submitReview(@RequestBody ReviewRequest request) {
        // TODO: Validate eligibility and save review
        return ResponseEntity.ok().build();
    }
}
