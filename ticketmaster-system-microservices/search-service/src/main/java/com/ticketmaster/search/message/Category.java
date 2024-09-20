package com.ticketmaster.search.message;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Category {


    private Integer id;


    private String name;


    private String description;


    private Set<Event> events;

}