package com.eventhub.venueDiscovery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenueInfo implements Serializable {
    private Long id;
    private String name;
    private String city;
    private Integer capacityMin;
    private Integer capacityMax;
    private Integer rate;
    private String address;

    public static VenueInfo fromVenue(com.eventhub.venueDiscovery.entity.Venue venue) {
        return new VenueInfo(
            venue.getId(),
            venue.getName(),
            venue.getCity(),
            venue.getCapacityMin(),
            venue.getCapacityMax(),
            venue.getRate(),
            venue.getAddress()
        );
    }
}
