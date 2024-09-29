package com.booking.service.ticket.openfeign;

import com.booking.service.ticket.dto.UpdateSeatsRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ticket-service",
        contextId = "ticketUpdateClient",
        url = "${application.config.ticket-service-url-feignClient}"
)

public interface TicketUpdateClient {
    @PutMapping("/update-seats")
     ResponseEntity<String> updateSeatsStatusToBooked(
            @Valid @RequestBody UpdateSeatsRequest updateSeatsRequest
    );
}
