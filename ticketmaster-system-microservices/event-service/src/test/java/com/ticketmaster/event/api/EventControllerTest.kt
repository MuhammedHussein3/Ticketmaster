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

}
