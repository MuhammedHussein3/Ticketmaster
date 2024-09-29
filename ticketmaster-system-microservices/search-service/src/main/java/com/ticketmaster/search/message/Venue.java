package com.ticketmaster.search.message;


import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Venue {

    private Integer id;

    private String name;

    private String description;

    private String location;

    private Integer capacity;

    private Set<Event> events;
}

