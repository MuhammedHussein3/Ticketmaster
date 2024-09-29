package com.ticketmaster.search.dto;

import lombok.Builder;

@Builder
public record VenueResponse(
        Integer id,
        String name,
        String description,
        String location,
        Integer totalSeats
) {
}