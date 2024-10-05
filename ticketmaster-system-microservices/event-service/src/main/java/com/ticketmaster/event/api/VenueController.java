package com.ticketmaster.event.api;

import com.ticketmaster.event.dto.VenueCreateRequest;
import com.ticketmaster.event.dto.VenueResponse;
import com.ticketmaster.event.dto.VenueUpdateRequest;
import com.ticketmaster.event.dto.VenuesDto;
import com.ticketmaster.event.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venues")
@RequiredArgsConstructor
@Validated
public class VenueController {

    private final VenueService venueService;


    @PostMapping
    public ResponseEntity<VenueResponse> createVenue(
            @Valid @RequestBody VenueCreateRequest createRequest
    ) {
        VenueResponse venueResponse = venueService.createVenue(createRequest);
        return ResponseEntity.ok(venueResponse);
    }

    @GetMapping("/{venue-id}")
    public ResponseEntity<VenueResponse> getVenueById(
            @PathVariable(name = "venue-id") Integer id
    ) {
        VenueResponse venueResponse = venueService.getVenueById(id);
        return ResponseEntity.ok(venueResponse);
    }

    @GetMapping
    public ResponseEntity<List<VenuesDto>> getAllVenues() {
        return ResponseEntity.ok(venueService.getAllVenues());
    }

    @PutMapping("/{venue-id}")
    public ResponseEntity<VenueResponse> updateVenue(
            @PathVariable(name = "venue-id") Integer id, @Valid @RequestBody VenueUpdateRequest updateRequest
    ) {
        VenueResponse venueResponse = venueService.updateVenue(id, updateRequest);
        return ResponseEntity.ok(venueResponse);
    }

    @DeleteMapping("/{venue-id}")
    public ResponseEntity<?> deleteVenue(
            @PathVariable(name = "venue-id") Integer id
    ) {
        venueService.deleteVenue(id);
        return ResponseEntity.ok().body("Deleted successfully");
    }



}
