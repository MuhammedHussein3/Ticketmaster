package com.ticket.service.entity;

import com.ticket.service.dto.SeatStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(
        name = "tickets",
        indexes = {
                @Index(name = "idx_seat_id", columnList = "seat_id"),
                @Index(name = "idx_status", columnList = "ticket_id, status"),
                @Index(name = "idx_section", columnList = "section"),
                @Index(name = "idx_row_number", columnList = "row, number")
        }
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;

    @Column(name = "user-id")
    private Long userId;
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "seat_id", nullable = false, length = 50)
    private String seatId;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SeatStatus status;

    @Column(name = "section", length = 50)
    private String section;

    @Column(name = "row", nullable = false)
    private int row;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", updatable = true)
    @Builder.Default
    private Instant updatedAt = Instant.now();
}
