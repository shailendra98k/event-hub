package com.eventhub.venueDiscovery.controller;


import com.eventhub.reviews.dto.ReviewPageResponse;
import com.eventhub.venueDiscovery.dto.VenueSearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {
    @GetMapping("")
    public ResponseEntity<List<VenueSearchResponse>> searchVenues(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String sortBy
    ) {
        // TODO: Implement venue search logic
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<ReviewPageResponse> getVenueReviews(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        // TODO: Implement paginated review fetch
        return ResponseEntity.ok(new ReviewPageResponse());
    }
}
