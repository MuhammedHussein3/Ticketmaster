package com.ticket.service.service;

import com.ticket.service.dto.TicketCreateRequest;
import com.ticket.service.dto.TicketResponse;
import com.ticket.service.entity.Ticket;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TicketService {

     CompletableFuture<List<Ticket>> processCreation(Long eventId, List<TicketCreateRequest> tickets) throws InterruptedException;
    // Create tickets for a specific event
    List<Ticket> createTicketsForEvent(Long eventId, List<TicketCreateRequest> tickets) throws InterruptedException;

    // Update seat status to booked
    void bookTickets(List<Long> ticketIds);

    // Find available tickets (optionally return TicketResponse instead of entity)
    List<Ticket> findAvailableTickets(List<Long> ticketIds);

    // Get tickets for a specific event
    List<TicketResponse> getTicketsByEvent(Long eventId);

    // Get tickets for a specific user
    List<TicketResponse> getTicketsByUser(Long userId);

   List<Ticket> getTicketsBooking(List<Long> ticketsId);
}

