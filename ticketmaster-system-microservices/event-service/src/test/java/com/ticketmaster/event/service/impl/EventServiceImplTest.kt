package com.ticketmaster.event.service.impl

import com.ticketmaster.event.dto.EventCreateRequest
import com.ticketmaster.event.dto.EventUpdateRequest
import com.ticketmaster.event.entity.Category
import com.ticketmaster.event.entity.Event
import com.ticketmaster.event.entity.Venue
import com.ticketmaster.event.exceptions.CategoryNotFoundException
import com.ticketmaster.event.exceptions.EventNotFoundException
import com.ticketmaster.event.exceptions.VenueNotFoundException
import com.ticketmaster.event.mapper.EventMapper
import com.ticketmaster.event.repository.EventRepository
import com.ticketmaster.event.repository.VenueRepository
import com.ticketmaster.event.service.CategoryService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.*
import org.mockito.kotlin.*

@ExtendWith(MockitoExtension::class)
class EventServiceImplTest {

    private val EVENT_ID = 1L
    private val CATEGORY_ID = 1
    private val VENUE_ID = 1

    @Mock
    lateinit var eventRepository: EventRepository

    @Mock
    lateinit var categoryService: CategoryService

    @Mock
    lateinit var venueRepository: VenueRepository

    @Mock
    lateinit var eventMapper: EventMapper

    @InjectMocks
    lateinit var eventServiceImpl: EventServiceImpl

    private lateinit var existingEvent: Event
    private lateinit var updateRequest: EventUpdateRequest

    @BeforeEach
    fun setUp() {
        existingEvent = Event().apply {
            id = EVENT_ID
            name = "Old Event"
            availableSeats = 10
        }

        updateRequest = EventUpdateRequest().apply {
            name = "New Event Name"
            categoryId = CATEGORY_ID
            venueId = VENUE_ID
            description = "Updated description"
            availableSeats = 100
        }
    }

    @Test
    fun `test create event success`() {
        val createRequest = EventCreateRequest(
            "Rock Concert", "The final match between them",
            LocalDateTime.now(), LocalDateTime.now().plusHours(2),
            200000, VENUE_ID, CATEGORY_ID
        )

        val category = Category()
        val venue = Venue()
        val event = Event()

        whenever(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(category)
        whenever(venueRepository.findById(VENUE_ID)).thenReturn(Optional.of(venue))
        whenever(eventMapper.mapToEvent(createRequest)).thenReturn(event)
        whenever(eventRepository.save(any())).thenReturn(event)

        val result = eventServiceImpl.createEvent(createRequest)

        assertNotNull(result, "Created event should not be null")
        verify(eventRepository, times(1)).save(event)
    }



}
