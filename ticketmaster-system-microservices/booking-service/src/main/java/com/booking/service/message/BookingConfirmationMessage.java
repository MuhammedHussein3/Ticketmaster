package com.booking.service.message;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record BookingConfirmationMessage(
         Long bookingId,
         Integer userId,
         Long eventId,
         LocalDateTime bookingTime,
         String eventName,
         String venueName,
         String venueLocation,
         String category,
         LocalDateTime eventDate
) {
}
