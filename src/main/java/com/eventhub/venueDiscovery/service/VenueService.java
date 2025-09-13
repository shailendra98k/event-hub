package com.eventhub.venueDiscovery.service;

import com.eventhub.auth.entity.User;
import com.eventhub.auth.repository.UserRepository;
import com.eventhub.venueDiscovery.dto.VenueInfo;
import com.eventhub.venueDiscovery.dto.VenueRequest;
import com.eventhub.venueDiscovery.entity.Venue;
import com.eventhub.venueDiscovery.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    public Venue createVenue(VenueRequest request) {
        Optional<User> user = userRepository.findById(request.getOwnerId());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Owner with ID " + request.getOwnerId() + " does not exist.");
        }

        if (user.get().getRole() != com.eventhub.auth.entity.Role.VENUE_OWNER) {
            throw new IllegalArgumentException("User with ID " + request.getOwnerId() + " is not a venue owner.");
        }

        Venue venue = Venue.builder()
                .capacityMax(request.getMaxCapacity())
                .capacityMin(request.getMinCapacity())
                .rate(request.getRate())
                .city(request.getCity())
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(Instant.now())
                .tags(request.getTags())
                .owner(user.get())
                .build();
        return venueRepository.save(venue);
    }

    public List<VenueInfo> getAllVenues(List<String> cities, List<String> tags, Integer minCapacity, Integer maxCapacity) {
        Specification<Venue> spec = null;

        String key = redisService.generateAllVenuesKey(cities, tags, minCapacity, maxCapacity);
        List<VenueInfo> cachedVenues = redisService.getCachedVenues(key);
        if (cachedVenues != null) {
            return cachedVenues;
        }
        if (cities != null && !cities.isEmpty()) {
            Specification<Venue> citiesSpec = (root, query, cb) -> root.get("city").in(cities);
            spec = (spec == null) ? citiesSpec : spec.and(citiesSpec);
        }
        if (tags != null && !tags.isEmpty()) {
            Specification<Venue> tagsSpec = (root, query, cb) -> root.join("tags").in(tags);
            spec = (spec == null) ? tagsSpec : spec.and(tagsSpec);
        }
        if (minCapacity != null) {
            Specification<Venue> minCapSpec = (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("capacityMin"), minCapacity);
            spec = (spec == null) ? minCapSpec : spec.and(minCapSpec);
        }
        if (maxCapacity != null) {
            Specification<Venue> maxCapSpec = (root, query, cb) -> cb.lessThanOrEqualTo(root.get("capacityMax"), maxCapacity);
            spec = (spec == null) ? maxCapSpec : spec.and(maxCapSpec);
        }
        List<Venue> venues = (spec == null) ? venueRepository.findAll() : venueRepository.findAll(spec);
        List<VenueInfo> result = new java.util.ArrayList<>();
        for (Venue venue : venues) {
            result.add(VenueInfo.fromVenue(venue));
        }
        redisService.cacheVenues(key, result);
        return result;

    }

}