package com.eventhub.shared.service;

import com.eventhub.venues.dto.VenueInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String generateAllVenuesKey(List<String> cities, List<String> tags, Integer minCapacity, Integer maxCapacity) {
        return "getAllVenues::" + String.join(",", Optional.ofNullable(cities).orElse(List.of("null"))) + "::"
                + String.join(",", Optional.ofNullable(tags).orElse(List.of("null"))) + "::"
                + (minCapacity != null ? minCapacity : "null") + "::"
                + (maxCapacity != null ? maxCapacity : "null");
    }

    public List<VenueInfo> getCachedVenues(String key) {
        Object cachedVenues = redisTemplate.opsForValue().get(key);
        if (cachedVenues instanceof List && !((List<?>) cachedVenues).isEmpty() && ((List<?>) cachedVenues).get(0) instanceof VenueInfo) {
            return (List<VenueInfo>) cachedVenues;
        }
        return null;
    }

    public void cacheVenues(String key, List<VenueInfo> venues) {
        redisTemplate.opsForValue().set(key, venues);
    }
}

