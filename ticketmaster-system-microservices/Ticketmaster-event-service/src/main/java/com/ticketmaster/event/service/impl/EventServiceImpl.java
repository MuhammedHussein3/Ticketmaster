package com.ticketmaster.event.service.impl;

import com.ticketmaster.event.dto.EventCreateRequest;
import com.ticketmaster.event.dto.EventCreateResponse;
import com.ticketmaster.event.dto.EventResponse;
import com.ticketmaster.event.dto.EventUpdateRequest;
import com.ticketmaster.event.entity.Category;
import com.ticketmaster.event.entity.Event;
import com.ticketmaster.event.entity.Venue;
import com.ticketmaster.event.exceptions.EventCreationException;
import com.ticketmaster.event.exceptions.EventNotFoundException;
import com.ticketmaster.event.repository.CategoryRepository;
import com.ticketmaster.event.repository.VenueRepository;
import com.ticketmaster.event.service.EventService;
import  com.ticketmaster.event.repository.EventRepository;
import com.ticketmaster.event.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link EventService} interface.
 *
 * This class provides the concrete implementation of the methods defined in the {@code EventService}
 * interface for managing events. It includes the logic for creating, retrieving, updating, and deleting
 * events, interacting with the underlying data store as necessary.
 *
 * Responsibilities:
 * - Handling event creation, including validation and persistence.
 * - Retrieving event details from the data store.
 * - Updating existing events with new information.
 * - Deleting events from the system.
 *
 * Dependencies:
 * - {@link EventRepository} for data access operations.
 * - {@link EventMapper} for mapping between request/response objects and entity objects.
 *
 * @see EventService
 * @see EventRepository
 * @see EventMapper
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final VenueRepository venueRepository;
    private final EventMapper eventMapper;

    @Override
    public EventCreateResponse createEvent(EventCreateRequest eventCreatedRequest) throws EventCreationException {

        Category category = categoryRepository.findById(eventCreatedRequest.categoryId()).get();
        Venue venue = venueRepository.findById(eventCreatedRequest.venueId()).get();

        Event event = Event.builder().name(eventCreatedRequest.name())
                .availableSeats(eventCreatedRequest.availableSeats())
                .description(eventCreatedRequest.description())
                .startTime(eventCreatedRequest.startTime())
                .endTime(eventCreatedRequest.endTime())
                .category(category)
                .venue(venue)
                .build();

        Event savedEntity = eventRepository.save(event);

        return EventCreateResponse.builder()
                .name(savedEntity.getName())
                .description(savedEntity.getDescription())
                .startTime(savedEntity.getStartTime())
                .endTime(savedEntity.getEndTime())
                .availableSeats(savedEntity.getAvailableSeats())
                .venueId(venue.getId())
                .categoryId(category.getId())
                .build();
    }

    @Override
    public EventResponse getEvent(Long eventId) throws EventNotFoundException {
        return null;
    }

    @Override
    public EventResponse updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) throws EventNotFoundException {
        return null;
    }

    @Override
    public void deleteEvent(Long eventId) {

    }
}
