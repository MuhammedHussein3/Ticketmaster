package com.ticketmaster.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VenuesDto {

    private String name;

    private String location;

    private Integer totalSeats;
}
