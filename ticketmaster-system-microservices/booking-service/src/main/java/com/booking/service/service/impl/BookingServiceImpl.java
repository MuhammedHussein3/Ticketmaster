package com.booking.service.service.impl;

import com.booking.service.dto.enums.BookingStatus;
import com.booking.service.dto.request.ReserveRequest;
import com.booking.service.dto.response.ReserveResponse;
import com.booking.service.entity.Booking;
import com.booking.service.event.dto.Event;
import com.booking.service.event.openFeign.EventInfoClient;
import com.booking.service.exceptions.BookingNotFoundException;
import com.booking.service.exceptions.TicketsNotAvailableException;
import com.booking.service.kafka.BookingProducer;
import com.booking.service.kafka.TicketProducer;
import com.booking.service.mapper.BookingMapper;
import com.booking.service.message.BookingConfirmationMessage;
import com.booking.service.message.TicketDetailsMessage;
import com.booking.service.repository.BookingRepository;
import com.booking.service.service.BookingService;
import com.booking.service.ticket.Ticket;
import com.booking.service.ticket.dto.UpdateSeatsRequest;
import com.booking.service.ticket.openfeign.TicketAvailabilityClient;
import com.booking.service.ticket.openfeign.TicketUpdateClient;
import com.booking.service.ticket.openfeign.TicketReservationClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * BookingServiceImpl is the implementation of the BookingService.
 * It handles booking reservations, ticket processing, and event communication.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final TicketAvailabilityClient ticketAvailabilityClient;
    private final TicketUpdateClient ticketUpdateClient;
    private final BookingProducer bookingProducer;
    private final TicketProducer ticketProducer;
    private final EventInfoClient eventInfoClient;
    private final TicketReservationClient ticketReservationClient;

    /**
     * Processes ticket reservation asynchronously using CircuitBreaker for resilience.
     *
     * @param reserveRequest Request for reserving tickets.
     * @return A CompletableFuture with ReserveResponse.
     * @throws InterruptedException In case of any interruption.
     */
    @CircuitBreaker(name = "getAvailableSeats", fallbackMethod = "getDefaultSeats")
    @Async("myThreadPoolExecutor")
    public CompletableFuture<ReserveResponse> processReserveTickets(ReserveRequest reserveRequest)
            throws InterruptedException {
        return reserveTickets(reserveRequest);
    }

    /**
     * Fallback method to provide default reserved seats in case of CircuitBreaker failure.
     *
     * @param e Exception that triggered the fallback.
     * @return A default ReserveResponse.
     */
    public ReserveResponse getDefaultSeats(Exception e) {
        return ReserveResponse.builder()
                .reservedTickets(List.of(1L, 1L))
                .bookingId(1L)
                .build();
    }

    /**
     * Reserves tickets for a booking request and saves the booking to the database.
     *
     * @param reserveRequest The request containing ticket reservation details.
     * @return A CompletableFuture with ReserveResponse.
     * @throws InterruptedException In case of any interruption.
     */
    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    TicketsNotAvailableException.class,
                    RuntimeException.class
            }
    )
    @Override
    public CompletableFuture<ReserveResponse> reserveTickets(ReserveRequest reserveRequest) throws InterruptedException {
        List<Long> reservedTicketIds = reserveRequest.getTicketIds();
        List<Ticket> availableTickets = ticketAvailabilityClient.getAvailableSeats(reservedTicketIds).getBody();

        log.info("Successfully linked booking service with ticket service.");

        if (availableTickets == null) {
            throw new TicketsNotAvailableException("One or more tickets are not available.");
        }

        Booking finalizedBooking = createAndSaveBooking(reserveRequest, availableTickets);
        return CompletableFuture.completedFuture(convertToReserveResponse(finalizedBooking));
    }

    /**
     * Confirms the booking by updating the ticket status and sending notifications.
     *
     * @param reservationId The ID of the booking reservation.
     * @throws ExecutionException    In case of errors during execution.
     * @throws InterruptedException In case of interruption.
     */
    @Transactional(
            propagation = Propagation.SUPPORTS,
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = {
                    BookingNotFoundException.class,
                    Exception.class,
                    IllegalArgumentException.class
            }
    )
    public void confirmBooking(Long reservationId) throws ExecutionException, InterruptedException {
        Booking booking = findBooking(reservationId);
        UpdateSeatsRequest updateSeatsRequest = UpdateSeatsRequest.builder()
                .ticketIds(booking.getTicketIds())
                .build();

        ticketUpdateClient.updateSeatsStatusToBooked(updateSeatsRequest);

        booking.setStatus(BookingStatus.CONFIRMED);
        Booking finalizedBooking = saveBooking(booking);

        Event event = getEvent(finalizedBooking.getEventId());
        List<Ticket> availableTickets = ticketReservationClient.getTicketsByReservation(booking.getTicketIds()).getBody();
        log.info("Successfully linked ticket service with booking service.");

        BookingConfirmationMessage bookingConfirmationMessage = convertToBookingConfirmationMessage(event, finalizedBooking);
        bookingProducer.sendBookingConfirmation(bookingConfirmationMessage);

        Map<Long, String> ticketSeatMap = mapTicketIdToSeat(availableTickets);
        TicketDetailsMessage ticketDetailsMessage = convertToTicketDetails(ticketSeatMap, event, booking);
        ticketProducer.sendTicketDetails(ticketDetailsMessage);

        log.info("Successfully sent ticket notifications.");
    }


    /**
     * Maps ticket IDs to their respective seat IDs.
     *
     * @param tickets The list of tickets.
     * @return A map of ticket IDs to seat IDs.
     */
    private Map<Long, String> mapTicketIdToSeat(List<Ticket> tickets) {
        Map<Long, String> ticketSeatMap = new HashMap<>();
        for (Ticket ticket : Objects.requireNonNull(tickets)) {
            ticketSeatMap.put(ticket.getId(), ticket.getSeatId());
        }
        return ticketSeatMap;
    }


    /**
     * Finds a booking by reservation ID.
     *
     * @param reservationId The reservation ID of the booking.
     * @return The Booking entity.
     */
    private Booking findBooking(Long reservationId) {
        return bookingRepository.findById(reservationId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
    }



    // Private utility methods for internal use.

    private ReserveResponse convertToReserveResponse(Booking finalizedBooking) {
        return bookingMapper.convertToReserveResponse(finalizedBooking);
    }

    private Booking createAndSaveBooking(ReserveRequest reserveRequest, List<Ticket> availableTickets) {
        BigDecimal totalAmount = calculateTotalTicketPrice(availableTickets);
        Booking booking = bookingMapper.convertToBookingEntity(reserveRequest, totalAmount);
        return bookingRepository.save(booking);
    }

    private BigDecimal calculateTotalTicketPrice(List<Ticket> availableTickets) {
        return availableTickets.stream()
                .map(ticket -> BigDecimal.valueOf(ticket.getPrice().doubleValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BookingConfirmationMessage convertToBookingConfirmationMessage(Event event, Booking booking) {
        return bookingMapper.convertToBookingConfirmationMessage(event, booking);
    }

    private TicketDetailsMessage convertToTicketDetails(Map<Long, String> ticketSeatMap, Event event, Booking booking) {
        return bookingMapper.convertToTicketDetailsMessage(ticketSeatMap, event, booking);
    }

    private Event getEvent(Long eventId) throws ExecutionException, InterruptedException {
        return eventInfoClient.getEvent(eventId).getBody();
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }
}
