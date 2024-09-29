package com.ticketmaster.event.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventCreateRequest(

        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Description is mandatory")
        String description,

        @NotNull(message = "Start time is mandatory")
        LocalDateTime startTime,

        @NotNull(message = "End time is mandatory")
        @Future(message = "End time must be in the future")
        LocalDateTime endTime,

        @NotNull(message = "Available seats is mandatory")
        @Positive(message = "Available seats must be greater than zero")
        @Min(1)
        Integer availableSeats,

        Integer venueId,

        Integer categoryId
){
}
