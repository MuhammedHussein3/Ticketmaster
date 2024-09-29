package com.booking.service.ticket.openfeign;

import com.booking.service.ticket.Ticket;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "ticket-service",
        contextId = "ticketsReservation",
        url = "${application.config.ticket-service-url-feignClient}"
)
public interface TicketReservationClient {

    @GetMapping("/reservation")
    ResponseEntity<List<Ticket>> getTicketsByReservation(@RequestParam List<Long> ticketIds);
}
