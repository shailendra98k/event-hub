package com.eventhub.rfps.dto;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
public class RfpRequest {
    @NotNull(message = "Venue ID is required")
    private Long venueId;

    @NotNull(message = "Event date is required")
    private Instant eventDate;

    @NotNull(message = "Headcount is required")
    @Min(value = 1, message = "Headcount must be at least 1")
    private Integer headcount;

    @NotNull(message = "Minimum budget is required")
    @Min(value = 0, message = "Budget must be non-negative")
    private Integer budgetMin;

    @NotNull(message = "Maximum budget is required")
    @Min(value = 0, message = "Budget must be non-negative")
    private Integer budgetMax;

    @Size(max = 1000, message = "Notes must be at most 1000 characters")
    private String notes;
}
