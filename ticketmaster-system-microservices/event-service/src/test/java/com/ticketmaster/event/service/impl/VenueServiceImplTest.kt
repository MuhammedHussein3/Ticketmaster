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
}
