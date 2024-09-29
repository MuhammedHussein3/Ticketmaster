package com.ticketmaster.event.dto;

import lombok.Builder;

import java.sql.Time;
import java.time.LocalDateTime;

@Builder
public record EventCreateResponse(
        Long eventId,
        String name,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Integer venueId,
        Integer categoryId,
        Integer availableSeats
) {
}
