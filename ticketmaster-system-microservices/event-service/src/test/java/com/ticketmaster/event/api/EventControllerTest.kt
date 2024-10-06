package com.ticketmaster.event.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ticketmaster.event.dto.EventCreateRequest
import com.ticketmaster.event.dto.EventUpdateRequest
import com.ticketmaster.event.entity.Event
import com.ticketmaster.event.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.properties.Delegates

@WebMvcTest(EventController::class)
class EventControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var eventService: EventService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private lateinit var event: Event
    private lateinit var existingEvent: Event
    private var eventId by Delegates.notNull<Long>()

    @BeforeEach
    fun setUp() {
        eventId = 1L
        event = Event.builder()
            .id(eventId)
            .name("Sample Event")
            .availableSeats(1000)
            .build()

        existingEvent = Event.builder()
            .id(2L)
            .name("Updated Event")
            .description("Updated event description")
            .availableSeats(20000)
            .build()
    }

    @Test
    fun `should create event`() {
        val request = EventCreateRequest.builder()
            .name("Sample Event")
            .description("A sample event description")
            .startTime(LocalDateTime.of(2024, 10, 10, 10, 0, 0))
            .endTime(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
            .availableSeats(1000)
            .build()

        whenever(eventService.createEvent(any())).thenReturn(event)

        mockMvc.perform(
            post("/api/v1/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(event.id))
            .andExpect(jsonPath("$.name").value(event.name))
            .andExpect(jsonPath("$.availableSeats").value(event.availableSeats))

        verify(eventService, times(1)).createEvent(any<EventCreateRequest>())
    }

    @Test
    fun `should get event`() {
        whenever(eventService.getEvent(eventId)).thenReturn(CompletableFuture.completedFuture(event))

        mockMvc.perform(get("/api/v1/events/$eventId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(event.id))
            .andExpect(jsonPath("$.name").value(event.name))
            .andExpect(jsonPath("$.availableSeats").value(event.availableSeats))

        verify(eventService, times(1)).getEvent(eventId)
    }

    @Test
    fun `should update event`() {
        val updateRequest = EventUpdateRequest.builder()
            .name("Updated Event")
            .description("Updated event description")
            .startTime(LocalDateTime.of(2024, 10, 10, 10, 0, 0))
            .endTime(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
            .availableSeats(20000)
            .build()

        whenever(eventService.updateEvent(eq(eventId), any())).thenReturn(existingEvent)

        mockMvc.perform(
            put("/api/v1/events/{event-id}", eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(existingEvent.name))
            .andExpect(jsonPath("$.availableSeats").value(existingEvent.availableSeats))

        verify(eventService, times(1)).updateEvent(eq(eventId), any<EventUpdateRequest>())
    }


}
