package com.ticketmaster.event.dto;

import lombok.Builder;

import java.sql.Time;
import java.time.LocalDateTime;

@Builder
public record EventCreateRequest(
        String name,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Integer availableSeats,
        Integer venueId,
        Integer categoryId
) {
}
