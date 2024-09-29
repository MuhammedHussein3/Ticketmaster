package com.ticket.service.dto;

public record SeatResponse(
         String seatId,
         String section,
         int row,
         int number,
         boolean available,
         double price

) {
}
