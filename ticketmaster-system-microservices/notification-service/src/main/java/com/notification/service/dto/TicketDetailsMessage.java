package com.notification.service.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
public record TicketDetailsMessage(
        Long reservedId,
        Map<Long, String> ticketIdSeat,
        Map<Long, User> userTicketInfo,
        Long eventId,
        String eventName,
        LocalDateTime eventDate,
        String venueLocation

) {
}
