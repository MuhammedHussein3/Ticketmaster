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
import com.ticketmaster.event.service.CategoryService;
import com.ticketmaster.event.service.EventService;
import  com.ticketmaster.event.repository.EventRepository;
import com.ticketmaster.event.mapper.EventMapper;
import com.ticketmaster.event.service.VenueService;
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
    private final CategoryService categoryService;
    private final VenueService venueService;
    private final EventMapper eventMapper;

    @Override
    public EventCreateResponse createEvent(EventCreateRequest eventCreatedRequest) throws EventCreationException {

        Category category = new Category();
        Venue venue = new Venue();

        Event event = mapToEvent(eventCreatedRequest);

        Event savedEntity = eventRepository.save(event);

        return mapToEventCreateResponse(savedEntity);
    }

    private Event mapToEvent(EventCreateRequest eventCreateRequest){
        return eventMapper.mapToEvent(eventCreateRequest);
    }

    private EventCreateResponse mapToEventCreateResponse(Event createEvent){
        return eventMapper.mapToEventCreateResponse(createEvent);
    }

    @Override
    public EventResponse getEvent(Long eventId) throws EventNotFoundException {
        Event event = findEventById(eventId);
        return EventResponse.builder()
                .name(event.getName())
                .description(event.getDescription())
                .availableSeats(event.getAvailableSeats())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .category(event.getCategory())
                .venue(event.getVenue())
                .build();

    }

    @Override
    public EventResponse updateEvent(Long eventId, EventUpdateRequest eventUpdateRequest) throws EventNotFoundException {

        Event event = findEventById(eventId);

        event.setAvailableSeats(eventUpdateRequest.availableSeats());
        event.setName(event.getName());
        event.setDescription(eventUpdateRequest.description());
        event.setStartTime(event.getStartTime());
        event.setEndTime(eventUpdateRequest.endTime());

        eventRepository.save(event);

        return mapToEventResponse(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                ()-> new EventNotFoundException(String.format("Event not found with ID:: %d", eventId))
        );
        eventRepository.delete(event);
    }

    private Event findEventById(Long eventId){
        return eventRepository.findById(eventId).orElseThrow(
                ()-> new EventNotFoundException(String.format("Event not found with ID:: %d", eventId))
        );
    }

    private EventResponse mapToEventResponse(Event  event){
        return eventMapper.mapToEventResponse(event);
    }
}
