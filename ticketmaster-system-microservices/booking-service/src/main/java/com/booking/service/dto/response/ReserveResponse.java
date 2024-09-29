package com.booking.service.dto.response;

import com.booking.service.dto.LockTicketDto;
import com.booking.service.dto.enums.BookingStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ReserveResponse(

        Long bookingId,

        Integer userId,

        List<Long> reservedTickets,

        BigDecimal totalAmount,

        BookingStatus status

) {
}
