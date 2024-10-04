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

    @Test
    fun `test create event venue not found`() {
        val createRequest = EventCreateRequest(
            "Rock Concert", "The final match between them",
            LocalDateTime.now(), LocalDateTime.now().plusHours(2),
            200000, VENUE_ID, CATEGORY_ID
        )

        whenever(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(Category())
        whenever(venueRepository.findById(VENUE_ID)).thenReturn(Optional.empty())

        assertThrows<VenueNotFoundException> {
            eventServiceImpl.createEvent(createRequest)
        }
    }

    @Test
    fun `test get event success`() {
        val event = Event()
        whenever(eventRepository.findById(EVENT_ID)).thenReturn(Optional.of(event))

        val result = eventServiceImpl.getEvent(EVENT_ID).get()

        assertNotNull(result, "Event should be found and not null")
        verify(eventRepository, times(1)).findById(EVENT_ID)
    }

    @Test
    fun `test get event not found`() {
        whenever(eventRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<EventNotFoundException> {
            eventServiceImpl.getEvent(1L).get()
        }

        verify(eventRepository, times(1)).findById(1L)
    }

    @Test
    fun `test update event success`() {
        val venue = Venue().apply {
            id = VENUE_ID
        }

        val category = Category().apply {
            id = CATEGORY_ID
        }

        whenever(eventRepository.findById(EVENT_ID)).thenReturn(Optional.of(existingEvent))
        whenever(venueRepository.findById(VENUE_ID)).thenReturn(Optional.of(venue))
        whenever(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(category)
        whenever(eventRepository.save(existingEvent)).thenReturn(existingEvent)

        val updatedEvent = eventServiceImpl.updateEvent(EVENT_ID, updateRequest)

        assertNotNull(updatedEvent, "Updated event should not be null")
        assertEquals("New Event Name", updatedEvent.name, "Event name should be updated")
        assertEquals(venue, updatedEvent.venue, "Venue should be updated")
        assertEquals(category, updatedEvent.category, "Category should be updated")

        verify(eventRepository, times(1)).save(existingEvent)
    }

    @Test
    fun `test update event venue not found`() {
        whenever(eventRepository.findById(EVENT_ID)).thenReturn(Optional.of(existingEvent))
        whenever(venueRepository.findById(VENUE_ID)).thenReturn(Optional.empty())

        assertThrows<VenueNotFoundException> {
            eventServiceImpl.updateEvent(EVENT_ID, updateRequest)
        }

        verify(eventRepository, never()).save(any())
    }


}
