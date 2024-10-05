package com.ticketmaster.event.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Builder
public record VenueUpdateRequest(

        String name,

        String description,

        String location,

        Integer totalSeats
) {
}
