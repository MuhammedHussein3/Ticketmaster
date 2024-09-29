package com.ticketmaster.event.mapper;

import com.ticketmaster.event.dto.VenueCreateRequest;
import com.ticketmaster.event.dto.VenueResponse;
import com.ticketmaster.event.entity.Venue;
import org.springframework.stereotype.Service;

@Service
public class VenueMapper {

    public VenueResponse mapToResponse(Venue savedVenue) {

        return VenueResponse.builder()
                .id(savedVenue.getId())
                .name(savedVenue.getName())
                .description(savedVenue.getDescription())
                .location(savedVenue.getLocation())
                .totalSeats(savedVenue.getCapacity())
                .build();
    }

    public Venue mapToVenue(VenueCreateRequest createRequest) {

        return Venue.builder()
                .name(createRequest.name())
                .description(createRequest.description())
                .location(createRequest.location())
                .capacity(createRequest.totalSeats())
                .build();
    }
}
