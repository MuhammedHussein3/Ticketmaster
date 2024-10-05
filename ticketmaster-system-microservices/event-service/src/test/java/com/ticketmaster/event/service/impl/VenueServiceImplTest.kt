package com.ticketmaster.event.service.impl

import com.ticketmaster.event.dto.VenueCreateRequest
import com.ticketmaster.event.dto.VenueResponse
import com.ticketmaster.event.dto.VenueUpdateRequest
import com.ticketmaster.event.dto.VenuesDto
import com.ticketmaster.event.entity.Venue
import com.ticketmaster.event.exceptions.VenueNotFoundException
import com.ticketmaster.event.mapper.VenueMapper
import com.ticketmaster.event.projection.Venues
import com.ticketmaster.event.repository.VenueRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class VenueServiceImplTest {

    @Mock
    lateinit var venueRepository: VenueRepository

    @Mock
    lateinit var venueMapper: VenueMapper

    @InjectMocks
    lateinit var venueService: VenueServiceImpl

    private lateinit var venueCreateRequest: VenueCreateRequest
    private lateinit var venueUpdateRequest: VenueUpdateRequest
    private lateinit var venue: Venue
    private lateinit var venueResponse: VenueResponse

    @BeforeEach
    fun setUp() {
        venueCreateRequest = VenueCreateRequest(
            "Stadium A",
            "Large Stadium",
            "http://location-a.com",
            10000
        )

        venue = Venue().apply {
            id = 1
            name = "Stadium A"
            description = "Large Stadium"
            location = "http://location-a.com"
            capacity = 10000
        }

        venueUpdateRequest = VenueUpdateRequest(
            "Stadium B",
            "Small Stadium",
            "http://location-b.com",
            5000
        )

        venueResponse = VenueResponse(
            1,
            "Stadium A",
            "Large Stadium",
            "http://location-a.com",
            10000
        )
    }

    @Test
    fun `test createVenue success`() {
        whenever(venueMapper.mapToVenue(venueCreateRequest)).thenReturn(venue)
        whenever(venueRepository.save(venue)).thenReturn(venue)
        whenever(venueMapper.mapToResponse(venue)).thenReturn(venueResponse)

        val result = venueService.createVenue(venueCreateRequest)

        assertNotNull(result)
        assertEquals("Stadium A", result.name)
        assertEquals(10000, result.totalSeats)
        assertEquals("http://location-a.com", result.location)
        assertEquals("Large Stadium", result.description)

        verify(venueRepository, times(1)).save(venue)
    }


}