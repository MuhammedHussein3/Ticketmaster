package com.ticketmaster.search.dto;

import com.ticketmaster.search.entity.SearchEvent;
import lombok.Builder;

import java.util.List;

@Builder
public record EventSearchResponse(

        List<SearchEvent> events,

        Integer pageNumber,

        Integer pageSize,

        long totalElement,

        int totalPages,

        boolean isLast
) {
}
