package com.ticketmaster.search.service;


import com.ticketmaster.search.dto.SearchEvent;
import com.ticketmaster.search.exceptions.EventCreationException;
/**
 * Service interface for managing search-related operations.
 * This service defines methods to handle event creation and indexing.
 */
public interface SearchService {

    /**
     * Creates a new event and indexes it for search functionality.
     *
     * @param eventCreateRequest the details of the event to be created,
     *                           including name, description, start time, end time,
     *                           available seats, venue, and category.
     * @throws EventCreationException if the event creation process encounters any issues.
     */
    void createEvent(SearchEvent eventCreateRequest);
}
