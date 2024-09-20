package com.ticketmaster.search.message;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Event implements Serializable {


    private Long id;

    private String name;


    private String description;


    private LocalDateTime startTime;


    private LocalDateTime endTime;


    private Integer availableSeats;


    private Instant createdAt;

    private Instant updateAt;


    private Venue venue;


    private Category category;
}

