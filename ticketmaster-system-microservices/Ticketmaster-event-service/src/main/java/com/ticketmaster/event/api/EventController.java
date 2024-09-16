package com.ticketmaster.event.api;

import com.ticketmaster.event.dto.EventCreateRequest;
import com.ticketmaster.event.dto.EventCreateResponse;
import com.ticketmaster.event.dto.EventResponse;
import com.ticketmaster.event.dto.EventUpdateRequest;
import com.ticketmaster.event.service.EventService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{event-id}")
    public ResponseEntity<EventResponse> getEVent(
            @PathVariable("event-id") Long eventId
    ){
        return ResponseEntity.ok(eventService.getEvent(eventId));
    }

    @PutMapping("/event-id")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable("event-id") Long eventId,
            @Valid @RequestBody EventUpdateRequest eventUpdateRequest
            ){
        return ResponseEntity.ok(eventService.updateEvent(eventId, eventUpdateRequest));
    }

}
