package com.eventhub.rfps.dto;

import java.time.Instant;

public class RfpRequest {
    public Long venueId;
    public Instant eventDate;
    public Integer headcount;
    public Integer budgetMin;
    public Integer budgetMax;
    public String notes;
}
