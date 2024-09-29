package com.ticketmaster.search.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchCriteria(

        String eventName,
        String location,
        String dateFrom,  // Start date
        String dateTo,
        String venueName,
        String description,
        List<String> keywords,
        String category
) {
}
