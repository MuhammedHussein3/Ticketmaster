package com.ticketmaster.search.client;

import com.ticketmaster.search.dto.VenueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "event-service",
        contextId = "venueClient",
        url = "${application.config.venue-url-feignClient}"
)
public interface VenueClient {
    @GetMapping("/{venue-id}")
     ResponseEntity<VenueResponse> getVenueById(
            @PathVariable(name = "venue-id") Integer id
    );
}
