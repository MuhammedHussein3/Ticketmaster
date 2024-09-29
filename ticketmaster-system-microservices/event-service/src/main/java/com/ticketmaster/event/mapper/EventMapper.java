package com.ticketmaster.event.mapper;

import com.ticketmaster.event.dto.EventCreateRequest;
import com.ticketmaster.event.dto.EventCreateResponse;
import com.ticketmaster.event.dto.EventResponse;
import com.ticketmaster.event.entity.Event;
import org.springframework.stereotype.Service;

@Service
public class EventMapper {

    public Event mapToEvent(EventCreateRequest eventCreateRequest){

        return Event.builder()
                .description(eventCreateRequest.description())
                .name(eventCreateRequest.name())
                .availableSeats(eventCreateRequest.availableSeats())
                .startTime(eventCreateRequest.startTime())
                .endTime(eventCreateRequest.endTime())
                .build();
    }

    public EventCreateResponse mapToEventCreateResponse(Event event){

        return EventCreateResponse.builder()
                .eventId(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .availableSeats(event.getAvailableSeats())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .build();
    }

    public EventResponse mapToEventResponse(Event event){

        return EventResponse.builder()
                .name(event.getName())
                .description(event.getDescription())
                .availableSeats(event.getAvailableSeats())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .venue(event.getVenue())
                .category(event.getCategory())
                .build();
    }
}
