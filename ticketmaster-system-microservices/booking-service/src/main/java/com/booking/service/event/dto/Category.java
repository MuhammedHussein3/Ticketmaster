package com.booking.service.event.dto;


import lombok.*;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Category implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private Set<Event> events;

}
