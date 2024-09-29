package com.ticketmaster.event.api;

import com.ticketmaster.event.dto.EventCreateRequest;
import com.ticketmaster.event.dto.EventUpdateRequest;
import com.ticketmaster.event.entity.Event;
import com.ticketmaster.event.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventService eventService;


    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody EventCreateRequest eventCreateRequest
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventService.createEvent(eventCreateRequest));
    }

    @GetMapping("/{event-id}")
    public ResponseEntity<Event> getEvent(
            @PathVariable("event-id") Long eventId
    ) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(eventService.getEvent(eventId).get());
    }

    @PutMapping("/{event-id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable("event-id") Long eventId,
            @Valid @RequestBody EventUpdateRequest eventUpdateRequest
            ){

        return ResponseEntity.ok(eventService.updateEvent(eventId, eventUpdateRequest));
    }

    @DeleteMapping("/{event-id}")
    public ResponseEntity<?> deleteEvent(
            @PathVariable("event-id") Long eventId
    ){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().body(
                String.format("Event with ID:: %d delete successfully", eventId)
        );
    }
}
