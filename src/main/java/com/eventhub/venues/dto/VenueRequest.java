package com.eventhub.venues.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class VenueRequest {
    private String name;
    private String city;
    private Integer minCapacity;
    private Integer maxCapacity;
    private Integer rate;
    private String address;
    private String description;
    private Long ownerId;
    private List<String> tags;
}

