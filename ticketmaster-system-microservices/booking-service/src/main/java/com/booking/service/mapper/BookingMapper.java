package com.booking.service.mapper;

import com.booking.service.User.User;
import com.booking.service.dto.enums.BookingStatus;
import com.booking.service.dto.request.ReserveRequest;
import com.booking.service.dto.response.ReserveResponse;
import com.booking.service.entity.Booking;
import com.booking.service.event.dto.Event;
import com.booking.service.message.BookingConfirmationMessage;
import com.booking.service.message.TicketDetailsMessage;
import com.booking.service.ticket.Ticket;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class BookingMapper {

    /**
     * Converts a Booking entity to a ReserveResponse DTO.
     *
     * @param booking the booking entity to convert
     * @return ReserveResponse DTO with reservation details
     */
    public ReserveResponse convertToReserveResponse(Booking booking) {
        return ReserveResponse.builder()
                .status(booking.getStatus())
                .userId(booking.getUserId())
                .totalAmount(booking.getAmount())
                .reservedTickets(booking.getTicketIds())
                .bookingId(booking.getId())
                .build();
    }

    /**
     * Converts a ReserveRequest DTO to a Booking entity.
     *
     * @param reserveRequest the reserve request DTO containing user inputs
     * @param calculatedTotalAmount the total amount calculated for the booking
     * @return Booking entity with the necessary fields populated
     */
    public Booking convertToBookingEntity(ReserveRequest reserveRequest, BigDecimal calculatedTotalAmount) {
        return Booking.builder()
                .amount(calculatedTotalAmount)
                .status(BookingStatus.IN_PROGRESS)
                .ticketIds(reserveRequest.getTicketIds())
                .userId(reserveRequest.getUserId())
                .paymentId(null)
                .eventId(reserveRequest.getEventId())
                .build();
    }

    public BookingConfirmationMessage convertToBookingConfirmationMessage(Event event, Booking booking){

        return BookingConfirmationMessage.builder()
                .eventName(event.getName())
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .bookingTime(booking.getCreatedAt())
                .venueLocation(event.getLocation())
                .venueName(event.getVenue().getName())
                .category(event.getCategory().getName())
                .build();
    }

    public TicketDetailsMessage convertToTicketDetailsMessage(Map<Long, String> ticketIdSeats, Event event, Booking booking, Map<Long, User> userTicketInfo){

        return TicketDetailsMessage.builder()
                .ticketIdSeat(ticketIdSeats)
                .reservedId(booking.getId())
                .eventDate(event.getStartTime())
                .eventName(event.getName())
                .venueLocation(event.getLocation())
                .eventId(event.getId())
                .userTicketInfo(userTicketInfo)
                .build();
    }
}
