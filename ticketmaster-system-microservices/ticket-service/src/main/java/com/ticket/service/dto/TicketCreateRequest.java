package com.ticket.service.dto;

import java.math.BigDecimal;

public record TicketCreateRequest(
         String seatId,
         String section,
         int row,
         int number,
         BigDecimal price
) {
}
