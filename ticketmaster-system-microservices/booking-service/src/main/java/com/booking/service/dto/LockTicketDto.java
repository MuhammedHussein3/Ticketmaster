package com.booking.service.dto;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LockTicketDto {

    private Long ticketId;

    private Instant lockExpiration;
}
