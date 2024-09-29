package com.ticket.service.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AvailableTicketsRequest(
        List<Long> ticketIds
) {
}
