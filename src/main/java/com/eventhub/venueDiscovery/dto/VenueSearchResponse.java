package com.eventhub.venueDiscovery.dto;

import java.util.List;

public class VenueSearchResponse {
    public Long id;
    public String name;
    public String city;
    public Integer capacityMin;
    public Integer capacityMax;
    public List<String> tags;
    public Double avgRating;
    public Integer reviewCount;
}
