package com.booking.service.ticket;

import com.booking.service.dto.enums.Status;
import com.booking.service.ticket.dto.SeatStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Ticket {

    private Long id;

    private Long userId;

    private Long eventId;

    private String seatId;

    private SeatStatus status;

    private String section;

    private int row;

    private int number;

    private BigDecimal price;

    private Instant createdAt;

    private Instant updatedAt;
}
