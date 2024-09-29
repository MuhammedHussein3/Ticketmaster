package com.ticketmaster.event.repository;

import com.ticketmaster.event.entity.Venue;
import com.ticketmaster.event.projection.Venues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {

    @Query("""
            SELECT v.name AS name, v.location AS location, v.capacity AS totalSeats
            FROM Venue v
            """)
    List<Venues> getAllVenues();
}
