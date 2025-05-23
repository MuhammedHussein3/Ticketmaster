package com.ticketmaster.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a request for updating an event.
 *
 * This class contains the details required to update an existing event, including
 * the name, description, available seats, start and end times, and related venue
 * and category IDs.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventUpdateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Event name cannot be blank")
    private String name;

    @Positive(message = "Available seats must be positive")
    private Integer availableSeats;

    @NotBlank(message = "Event description cannot be blank")
    private String description;

    @NotNull(message = "Event start time cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "Event end time cannot be null")
    private LocalDateTime endTime;

    @NotNull(message = "Venue ID cannot be null")
    @Positive(message = "Venue ID must be positive")
    private Integer venueId;

    @NotNull(message = "Category ID cannot be null")
    @Positive(message = "Category ID must be positive")
    private Integer categoryId;
}
