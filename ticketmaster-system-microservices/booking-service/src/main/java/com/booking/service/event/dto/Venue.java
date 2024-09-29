package com.booking.service.event.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Venue implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private String location;

    private Integer capacity;

    private Set<Event> events;
}

