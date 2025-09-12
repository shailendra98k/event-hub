package com.eventhub.venueDiscovery.controller;

import com.eventhub.venueDiscovery.dto.VenueFilterRequest;
import com.eventhub.venueDiscovery.dto.VenueRequest;
import com.eventhub.venueDiscovery.entity.Venue;
import com.eventhub.venueDiscovery.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venues")
public class VenueController {
    @Autowired
    private VenueService venueService;

    @PostMapping
    public ResponseEntity<?> createVenue(@RequestBody VenueRequest request) {

        try {
            Venue venue = venueService.createVenue(request);
            return ResponseEntity.ok(venue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<?>> getAllVenues(
            @RequestParam(required = false) List<String> city,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity) {
        List<Venue> venues = venueService.getAllVenues(city, tags, minCapacity, maxCapacity);
        return ResponseEntity.ok(venues);
    }
}
