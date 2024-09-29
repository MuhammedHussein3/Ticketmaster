package com.booking.service.ticket.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AvailableTicketsRequest(
        List<Long> ticketIds
) {
}
