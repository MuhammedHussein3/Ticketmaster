package com.ticket.service.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TicketResponse(
         Long ticketId,
         Long eventId,
         String seatId,
         String status,
         BigDecimal price
) {
}
