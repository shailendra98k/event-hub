package com.eventhub.venues.controller;

import com.eventhub.venues.dto.VenueInfo;
import com.eventhub.venues.dto.VenueRequest;
import com.eventhub.venues.entity.Venue;
import com.eventhub.venues.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venues")
public class VenueController {
    @Autowired
    private VenueService venueService;

//    @PreAuthorize("hasRole('ADMIN')")
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
        List<VenueInfo> venues = venueService.getAllVenues(city, tags, minCapacity, maxCapacity);
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVenueById(@PathVariable Long id) {
        VenueInfo venueInfo = venueService.getVenueById(id);
        if (venueInfo != null) {
            return ResponseEntity.ok(venueInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
