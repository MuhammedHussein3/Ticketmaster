package com.ticketmaster.event.service;


import com.ticketmaster.event.dto.EventCreateRequest;
import com.ticketmaster.event.dto.EventCreateResponse;
import com.ticketmaster.event.dto.EventResponse;


/**
 * Service interface for managing events.
 */
public interface EventService {


    /**
     * Create a new event based on the provided request.
     *
     * @param eventCreatedRequest the request containing details of the event to be created.
     * @return the response containing details of the event created.
     */
    EventCreateResponse createEvent(EventCreateRequest eventCreatedRequest);

    /**
     * Retrieves detailed information about an event.
     * @param eventId the id of an event.
     * @return Event details including available seats, location, start time and end time.
     */
    EventResponse getEvent(Long eventId);
}
