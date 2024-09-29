package com.booking.service.event.openFeign;

import com.booking.service.event.dto.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

@FeignClient(
        name = "event-service",
        contextId = "eventClient",
        url = "${application.config.event-service-url-feignClient}"
)
public interface EventInfoClient {

    @GetMapping("/{event-id}")
     ResponseEntity<Event> getEvent(
            @PathVariable("event-id") Long eventId
    )throws ExecutionException, InterruptedException ;

}
