package com.ticketmaster.event.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

@Builder
public record VenueCreateRequest(

        @NotNull
        @Size(max = 255)
        String name,

        @NotNull
        String description,

        @NotNull
        @URL(message = "Location must be a valid URL")
        String location,

        @NotNull
        @Min(1)
        Integer totalSeats
) {
}
