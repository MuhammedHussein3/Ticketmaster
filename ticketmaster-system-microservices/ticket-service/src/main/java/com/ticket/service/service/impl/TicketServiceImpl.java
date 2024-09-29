package com.ticket.service.service.impl;

import com.ticket.service.dto.SeatStatus;
import com.ticket.service.dto.TicketCreateRequest;
import com.ticket.service.dto.TicketResponse;
import com.ticket.service.entity.Ticket;
import com.ticket.service.exception.TicketsNotFoundException;
import com.ticket.service.mapper.Mapper;
import com.ticket.service.repository.TicketRepository;
import com.ticket.service.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repository;
    private final Mapper mapper;


    @Async("myThreadPoolExecutor")
    public CompletableFuture<List<Ticket>> processCreation(Long eventId, List<TicketCreateRequest> tickets) throws InterruptedException {
        synchronized (this){
            return CompletableFuture.completedFuture(createTicketsForEvent(eventId, tickets));
        }
    }
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @CacheEvict(
            value = {
                    "availableTicketsCache",
                    "eventTicketsCache",
                    "userTicketsCache",
                    "bookedTicketsCache"
            },
            allEntries = true
    )
    @Override
    public List<Ticket> createTicketsForEvent(Long eventId, List<TicketCreateRequest> tickets) throws InterruptedException {
        List<Ticket> finalizedBooking  =  mapper.convertToTickets(eventId, tickets);
        repository.saveAll(finalizedBooking );
        return finalizedBooking;
    }


    @Async("myThreadPoolExecutor")
    @Transactional
    @CacheEvict(
            value = {
                    "availableTicketsCache",
                    "eventTicketsCache",
                    "userTicketsCache",
                    "bookedTicketsCache"
            },
            allEntries = true
    )
    @Override
    public void bookTickets(List<Long> ticketIds) {

        synchronized (this){
            repository.updateSeatStatusToBooked(SeatStatus.BOOKED, ticketIds);
        }

    }

    private <T> List<List<T>> partitionList(List<T> list, int batchSize) {
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += batchSize) {
            partitions.add(list.subList(i, Math.min(i + batchSize, list.size())));
        }
        return partitions;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Cacheable(value = "availableTicketsCache", key = "#ticketIds")
    @Override
    public List<Ticket> findAvailableTickets(List<Long> ticketIds) {

        List<List<Long>> batches = partitionList(ticketIds, 10);
        List<Ticket> tickets = new ArrayList<>();

        for (List<Long> batch : batches){
            tickets.addAll(repository.findAvailableSeats(batch, SeatStatus.AVAILABLE));
        }

       return tickets.size() == ticketIds.size() ? tickets
                                                     : null;
    }

    @Cacheable(value = "eventTicketsCache", key = "#eventId")
    @Override
    public List<TicketResponse> getTicketsByEvent(Long eventId) {

        List<Ticket> tickets = repository.findTicketsByEvent(eventId)
                .orElseThrow(() -> new TicketsNotFoundException("Tickets not found for eventId: " + eventId));
        return mapper.convertToTicketResponse(tickets);
    }

    @Cacheable(value = "userTicketsCache", key = "#userId")
    @Override
    public List<TicketResponse> getTicketsByUser(Long userId) {

        List<Ticket> tickets = repository.findTicketsByUser(userId)
                .orElseThrow(() -> new TicketsNotFoundException("Tickets not found for userId: " + userId));
        return mapper.convertToTicketResponse(tickets);
    }

    @Cacheable(value = "bookedTicketsCache", key = "#ticketsId")
    @Override
    public List<Ticket> getTicketsBooking(List<Long> ticketsId) {
        return repository.findReservationSeats(ticketsId, SeatStatus.BOOKED);
    }


}
