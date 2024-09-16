package com.ticketmaster.event.api;

import com.ticketmaster.event.dto.EventCreateRequest;
import com.ticketmaster.event.dto.EventCreateResponse;
import com.ticketmaster.event.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;


    @PostMapping
    public ResponseEntity<EventCreateResponse> createEvent(
            @Valid @RequestBody EventCreateRequest eventCreateRequest
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.createEvent(eventCreateRequest));
    }

}
