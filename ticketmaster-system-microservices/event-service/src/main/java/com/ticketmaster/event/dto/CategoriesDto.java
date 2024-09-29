package com.ticketmaster.event.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoriesDto {
    private String name;
    private String description;
}
