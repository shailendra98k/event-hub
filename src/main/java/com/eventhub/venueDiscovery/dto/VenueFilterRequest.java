package com.eventhub.venueDiscovery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Data
@AllArgsConstructor
@Getter
public class VenueFilterRequest {
    private String city;
    private List<String> tags;
    private Integer minCapacity;
    private Integer maxCapacity;
}

