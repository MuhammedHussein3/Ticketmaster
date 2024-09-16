package com.ticketmaster.event.service;


import com.ticketmaster.event.dto.EventCreateRequest;
import com.ticketmaster.event.dto.EventCreateResponse;
import com.ticketmaster.event.dto.EventResponse;
import com.ticketmaster.event.dto.EventUpdateRequest;
import com.ticketmaster.event.exceptions.EventNotFoundException;
import com.ticketmaster.event.exceptions.EventCreationException;
import com.ticketmaster.event.exceptions.InvalidEventUpdateException;
/**
 * Service interface for managing events.
 */
public interface EventService {


    /**
     * Creates a new event based on the provided request.
     *
     * This method initializes a new event with the details provided in the {@code eventCreateRequest},
     * such as name, description, start time, end time, and the number of available seats. The newly
     * created event will be persisted in the system.
     *
     * @param eventCreatedRequest the request object containing the details of the event to be created,
     *                           including name, description, start time, end time, available seats, and more.
     * @return the response object containing details of the created event, including the event's ID
     *         and other information such as timestamps and status.
     * @throws EventCreationException if any error occurs during event creation or if the provided data is invalid.
     */
    EventCreateResponse createEvent(EventCreateRequest eventCreatedRequest) throws EventCreationException;

    /**
     * Retrieves detailed information about an event.
     *
     * This method fetches the full details of an event identified by the provided {@code eventId}.
     * The returned information includes the event's name, available seats, location, start time,
     * end time, and other relevant details.
     *
     * @param eventId the unique identifier of the event to retrieve.
     * @return the {@code EventResponse} containing all details of the event, such as name,
     *         description, available seats, location, start time, and end time.
     * @throws EventNotFoundException if no event with the specified {@code eventId} is found.
     */
    EventResponse getEvent(Long eventId) throws EventNotFoundException;


    /**
     * updates the details of an existing event.
     *
     * This method allows updating an event's properties such as name, available seats, start time,
     * end time, and other relevant information. The event is identified by its unique ID, and
     * any changes to its details are passed through the {@code eventUpdateRequest} object.
     *
     * @param eventId the unique identifier of the event to update.
     *                This must correspond to an existing event in the system.
     * @param eventUpdateRequest the request object containing updated details of the event.
     *                           Fields such as name, available seats, start time, end time,
     *                           and other event-specific information can be modified.
     * @return the updated event details wrapped in an {@code EventResponse} object, reflecting the
     *         new state of the event after applying the changes.
     * @throws EventNotFoundException if no event with the provided {@code eventId} is found.
     * @throws InvalidEventUpdateException if the provided update request contains invalid or inconsistent data.
     */

    EventResponse updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) throws EventNotFoundException;
}
