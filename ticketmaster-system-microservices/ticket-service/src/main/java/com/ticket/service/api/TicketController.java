package com.ticket.service.api;

import com.ticket.service.dto.*;
import com.ticket.service.entity.Ticket;
import com.ticket.service.service.TicketService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Validated
public class TicketController {

    private final TicketService service;

    /**
     * Create tickets for an event.
     *
     * @param eventId  ID of the event for which tickets are being created.
     * @param tickets  List of tickets to be created.
     * @return ResponseEntity with created tickets.
     */
    @PostMapping("/event/{event-id}/create")
    public ResponseEntity<List<Ticket>> createTicketsForEvent(
            @PathVariable(name = "event-id") Long eventId,
            @Valid @RequestBody List<TicketCreateRequest> tickets
    ) throws InterruptedException, ExecutionException {
        List<Ticket> createdTickets = service.processCreation(eventId, tickets).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTickets);
    }

    /**
     * Update the status of tickets to booked.
     *
     * @param updateSeatsRequest Request containing the list of ticket IDs to update.
     * @return ResponseEntity with the update status message.
     */
    @PutMapping("/update-seats")
    public ResponseEntity<String> updateSeatsStatusToBooked(@Valid @RequestBody UpdateSeatsRequest updateSeatsRequest) {
        service.bookTickets(updateSeatsRequest.ticketIds());
        return ResponseEntity.ok("Seats status updated to booked");
    }

    @GetMapping("/available-seats")
    public ResponseEntity<List<Ticket>> getAvailableSeats(
            @RequestParam List<Long> ticketIds
    ) {
        List<Ticket> availableSeats = service.findAvailableTickets(ticketIds);
        return ResponseEntity.ok(availableSeats);
    }


    /**
     * Get all tickets for a specific event.
     *
     * @param eventId ID of the event.
     * @return ResponseEntity with tickets for the event.
     */
    @GetMapping("/event/{event-id}")
    public ResponseEntity<List<TicketResponse>> getTicketsByEvent(@PathVariable("event-id") Long eventId) {
        List<TicketResponse> tickets = service.getTicketsByEvent(eventId);
        return ResponseEntity.ok(tickets);
    }

    /**
     * Get tickets purchased by a specific user.
     *
     * @param userId ID of the user.
     * @return ResponseEntity with tickets for the user.
     */
    @GetMapping("/user/{user-id}")
    public ResponseEntity<List<TicketResponse>> getTicketsByUser(@PathVariable("user-id") Long userId) {
        List<TicketResponse> tickets = service.getTicketsByUser(userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/reservation")
    public ResponseEntity<List<Ticket>> getTicketsByReservation(@RequestParam List<Long> ticketIds) {
        return ResponseEntity.ok(service.getTicketsBooking(ticketIds));
    }
}
