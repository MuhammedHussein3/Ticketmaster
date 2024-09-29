package com.booking.service.ticket.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record UpdateSeatsRequest(
        @NotEmpty(message = "Ticket IDs cannot be empty")
        List<Long> ticketIds
) {
}
