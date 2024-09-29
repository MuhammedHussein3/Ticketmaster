package com.notification.service.dto;

import lombok.Builder;

@Builder
public record QRCodeDetails(
        Long reservedId,
        Long ticketId,
        String firstName,
        String lastName,
        int age,
        String seatId,
        Integer userId
) {
}
