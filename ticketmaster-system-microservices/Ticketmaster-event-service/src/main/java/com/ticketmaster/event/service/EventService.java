package com.ticketmaster.event.service;

import com.ticketmaster.event.dto.EventCreatRequest;
import com.ticketmaster.event.dto.EventCreateResponse;


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
    EventCreateResponse createEvent(EventCreatRequest eventCreatedRequest);
}
