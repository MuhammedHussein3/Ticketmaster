package com.ticketmaster.search.service;



import com.ticketmaster.search.message.EventMessageCDC;
import com.ticketmaster.search.exceptions.EventCreationException;
/**
 * Service interface for managing search-related operations.
 * This service defines methods to handle event creation and indexing.
 */
public interface EventCDCService {

    /**
     * Creates a new event and indexes it for search functionality.
     *
     *                           including name, description, start time, end time,
     *                           available seats, venue, and category.
     * @throws EventCreationException if the event creation process encounters any issues.
     */
    void createEvent(EventMessageCDC messageCDC);

    void updateEvent(EventMessageCDC messageCDC);

    void deleteEvent(EventMessageCDC messageCDC);
}
