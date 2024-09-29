package com.ticketmaster.event.service.impl;

import com.ticketmaster.event.dto.EventCreateRequest;
import com.ticketmaster.event.dto.EventCreateResponse;
import com.ticketmaster.event.dto.EventUpdateRequest;
import com.ticketmaster.event.entity.Category;
import com.ticketmaster.event.entity.Event;
import com.ticketmaster.event.entity.Venue;
import com.ticketmaster.event.exceptions.EventCreationException;
import com.ticketmaster.event.exceptions.EventNotFoundException;
import com.ticketmaster.event.exceptions.VenueNotFoundException;
import com.ticketmaster.event.repository.VenueRepository;
import com.ticketmaster.event.service.CategoryService;
import com.ticketmaster.event.service.EventService;
import  com.ticketmaster.event.repository.EventRepository;
import com.ticketmaster.event.mapper.EventMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private final VenueRepository venueRepository;
    private final EventMapper eventMapper;




    @CacheEvict(
            value = "getEventCache",
            allEntries = true
    )
    @Transactional
    @Override
    public Event createEvent(EventCreateRequest createRequest) throws EventCreationException {

        Category category = categoryService.getCategoryById(createRequest.categoryId());
        Venue venue = findVenue(createRequest.venueId());

        Event event = mapToEvent(createRequest);
        event.setCategory(category);
        event.setVenue(venue);

        return eventRepository.save(event);
    }


    private Event mapToEvent(EventCreateRequest eventCreateRequest){
        return eventMapper.mapToEvent(eventCreateRequest);
    }

    private EventCreateResponse mapToEventCreateResponse(Event createEvent){
        return eventMapper.mapToEventCreateResponse(createEvent);
    }

    @Cacheable(
            value = "getEventCache",
            key = "#eventId",
            unless = "#result == null"
    )
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {
                    EventNotFoundException.class
            }
    )
    @Async
    @Override
    public CompletableFuture<Event> getEvent(Long eventId) throws EventNotFoundException {

        Event event = findEventById(eventId);

        return CompletableFuture.completedFuture(event);
    }


    @CacheEvict(
            value = "getEventCache",
            allEntries = true
    )
    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            rollbackFor = {
                    EventNotFoundException.class
            }
    )
    @Override
    public Event updateEvent(Long eventId, EventUpdateRequest updateRequest) throws EventNotFoundException {

        Event event = findEventById(eventId);

        mergeEventDetails(event, updateRequest);

        return eventRepository.save(event);
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    private void mergeEventDetails(Event existingEvent, EventUpdateRequest updateRequest) {

        if (StringUtils.isNotBlank(updateRequest.getName())) {
            existingEvent.setName(updateRequest.getName());
        }

        if (StringUtils.isNotBlank(updateRequest.getDescription())) {
            existingEvent.setDescription(updateRequest.getDescription());
        }

        if (updateRequest.getStartTime() != null) {
            existingEvent.setStartTime(updateRequest.getStartTime());
        }

        if (updateRequest.getEndTime() != null) {
            existingEvent.setEndTime(updateRequest.getEndTime());
        }

        if (updateRequest.getAvailableSeats() != null) {
            existingEvent.setAvailableSeats(updateRequest.getAvailableSeats());
        }

        if (updateRequest.getVenueId() != null) {
            Venue venue = venueRepository.findById(updateRequest.getVenueId()).orElseThrow(
                    () -> new VenueNotFoundException(String.format("Venue not found with id::%d", updateRequest.getVenueId()))
            );
            existingEvent.setVenue(venue);
        }

        if (updateRequest.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(updateRequest.getCategoryId());
            existingEvent.setCategory(category);
        }
    }



    @CacheEvict(
            value = "getEventCache",
            allEntries = true
    )
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


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = {VenueNotFoundException.class})
    private Venue findVenue(Integer id){
        return venueRepository.findById(id)
                .orElseThrow(()-> new VenueNotFoundException(String.format("Venue not found with ID:: %d", id)));
    }
}
