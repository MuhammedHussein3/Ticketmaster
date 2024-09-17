package com.ticketmaster.event.service;

import com.ticketmaster.event.dto.VenueCreateRequest;
import com.ticketmaster.event.dto.VenueResponse;
import com.ticketmaster.event.entity.Venue;

/**
 * Service interface for managing venues
 */
public interface VenueService {

    /**
     * Creates a new venue based on the provided request.
     *
     * This method initializes a new event with the details provided in the {@code createRequest},
     * such as name, description, location, totalSeats
     * @param createRequest the request object containing the details of the venue to be created,
     * included name, description, location, total seats
     * @return the response object containing details of the created venue, including the venue's ID
     *and other information such as location.
     */
    VenueResponse createVenue(VenueCreateRequest createRequest);

    /**
     * Retrieves the details of a venue by its ID.
     *
     * @param id the unique identifier of the venue
     * @return the response object containing details of the requested venue
     */
    VenueResponse getVenueById(Integer id);

    /**
     * Updates an existing venue with new details.
     *
     * @param id the unique identifier of the venue to update
     * @param updateRequest the request object containing the updated details of the venue
     * @return the response object containing the updated details of the venue
     */
    VenueResponse updateVenue(Integer id, VenueCreateRequest updateRequest);

    /**
     * Deletes a venue based on its ID.
     *
     * @param id the unique identifier of the venue to delete
     */
    void deleteVenue(Integer id);
}
