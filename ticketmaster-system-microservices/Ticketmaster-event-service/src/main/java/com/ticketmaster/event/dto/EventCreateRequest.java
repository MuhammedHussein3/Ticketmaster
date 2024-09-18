package com.ticketmaster.event.dto;

import jakarta.validation.constraints.Min;
import lombok.Builder;

import java.io.Serial;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventCreateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Event name cannot be blank")
    private String name;

    @NotBlank(message = "Event description cannot be blank")
    private String description;

    @NotNull(message = "Event start time cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "Event end time cannot be null")
    private LocalDateTime endTime;

    @NotNull(message = "Available seats cannot be null")
    @Positive(message = "Available seats must be positive")
    @Min(1)
    private Integer availableSeats;

    @NotNull(message = "Venue ID cannot be null")
    @Positive(message = "Venue ID must be positive")
    private Integer venueId;

    @NotNull(message = "Category ID cannot be null")
    @Positive(message = "Category ID must be positive")
    private Integer categoryId;
}

