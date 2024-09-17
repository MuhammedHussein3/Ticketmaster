package com.ticketmaster.event.service.impl;

import com.ticketmaster.event.dto.VenueCreateRequest;
import com.ticketmaster.event.dto.VenueResponse;
import com.ticketmaster.event.entity.Venue;
import com.ticketmaster.event.exceptions.VenueNotFoundException;
import com.ticketmaster.event.mapper.VenueMapper;
import com.ticketmaster.event.repository.VenueRepository;
import com.ticketmaster.event.service.VenueService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public VenueResponse createVenue(VenueCreateRequest createRequest) {

        Venue venue = mapToVenue(createRequest);

        Venue savedVenue = venueRepository.save(venue);

        return mapToResponse(savedVenue);
    }

    private Venue mapToVenue(VenueCreateRequest createRequest){
        return venueMapper.mapToVenue(createRequest);
    }

    private VenueResponse mapToResponse(Venue savedVenue){
        return venueMapper.mapToResponse(savedVenue);
    }

    @Override
    public VenueResponse getVenueById(Integer id) {

        Venue venue =  findVenue(id);

        return mapToResponse(venue);
    }

    @Override
    public VenueResponse updateVenue(Integer id, VenueCreateRequest updateRequest) {

        Venue venue = findVenue(id);

        mergeVenueDetails(venue, updateRequest);

        Venue updatedVenue = venueRepository.save(venue);

        return mapToResponse(updatedVenue);
    }


}
