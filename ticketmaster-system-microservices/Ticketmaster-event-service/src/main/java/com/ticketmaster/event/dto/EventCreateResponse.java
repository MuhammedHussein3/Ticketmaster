package com.ticketmaster.event.dto;

import lombok.Builder;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;

@Builder
public class EventCreateResponse implements Serializable {
        private Long eventId;

        private String name;

        private String description;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private Integer venueId;

        private Integer categoryId;

        private Integer availableSeats;
}
