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
import com.ticketmaster.event.service.CategoryService;
import com.ticketmaster.event.service.EventService;
import  com.ticketmaster.event.repository.EventRepository;
import com.ticketmaster.event.mapper.EventMapper;
import com.ticketmaster.event.service.VenueService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

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


    @Transactional
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

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {EventNotFoundException.class}
    )
    @Async
    @Override
    public CompletableFuture<EventResponse> getEvent(Long eventId) throws EventNotFoundException {

        Event event = findEventById(eventId);

        return CompletableFuture.completedFuture(mapToEventResponse(event));
    }


    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {EventNotFoundException.class}
    )
    @Override
    public EventResponse updateEvent(Long eventId, EventUpdateRequest updateRequest) throws EventNotFoundException {

        Event event = findEventById(eventId);

        mergeEventDetails(event, updateRequest);

        eventRepository.save(event);

        return mapToEventResponse(event);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    private void mergeEventDetails(Event existingEvent, EventUpdateRequest updateRequest) {

        // Update event name if provided
        if (StringUtils.isNotBlank(updateRequest.name())) {
            existingEvent.setName(updateRequest.name());
        }

        // Update event description if provided
        if (StringUtils.isNotBlank(updateRequest.description())) {
            existingEvent.setDescription(updateRequest.description());
        }

        // Update start time if provided
        if (updateRequest.startTime() != null) {
            existingEvent.setStartTime(updateRequest.startTime());
        }

        // Update end time if provided
        if (updateRequest.endTime() != null) {
            existingEvent.setEndTime(updateRequest.endTime());
        }

        // Update available seats if provided
        if (updateRequest.availableSeats() != null) {
            existingEvent.setAvailableSeats(updateRequest.availableSeats());
        }

        // Update venue if a valid venue ID is provided
        if (updateRequest.venueId() != null) {
//            Venue venue = venueService.getVenue(updateRequest.venueId());
            Venue venue = new Venue();
            existingEvent.setVenue(venue);
        }

        // Update category if a valid category ID is provided
        if (updateRequest.categoryId() != null) {
//            Category category = categoryService.getCategory(updateRequest.categoryId());
            Category category = new Category();
            existingEvent.setCategory(category);
        }
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = {EventNotFoundException.class}
    )
    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                ()-> new EventNotFoundException(String.format("Event not found with ID:: %d", eventId))
        );
        eventRepository.delete(event);
    }

    @Transactional(
            propagation = Propagation.SUPPORTS
    )
    private Event findEventById(Long eventId){
        return eventRepository.findById(eventId).orElseThrow(
                ()-> new EventNotFoundException(String.format("Event not found with ID:: %d", eventId))
        );
    }

    private EventResponse mapToEventResponse(Event  event){
        return eventMapper.mapToEventResponse(event);
    }
}
