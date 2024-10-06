package com.ticketmaster.event.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ticketmaster.event.dto.VenueCreateRequest
import com.ticketmaster.event.dto.VenueResponse
import com.ticketmaster.event.dto.VenueUpdateRequest
import com.ticketmaster.event.dto.VenuesDto
import com.ticketmaster.event.service.VenueService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(VenueController::class)
class VenueControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var venueService: VenueService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var venueResponse: VenueResponse
    private lateinit var venueCreateRequest: VenueCreateRequest
    private lateinit var venueUpdateRequest: VenueUpdateRequest

    @BeforeEach
    fun setUp() {
        venueResponse = VenueResponse(
             1,
             "Madison Square Garden",
             "New York",
             "https://goo.gl/maps/centralpark",
            20000
        )

        venueCreateRequest = VenueCreateRequest(
            "Madison Square Garden",
            "New York",
            "https://goo.gl/maps/centralpark",
            20000
        )

        venueUpdateRequest = VenueUpdateRequest(
             "Updated Venue",
             "Updated description",
             "Updated Location",
            10000
        )
    }

    @Test
    fun `should create venue`() {
        `when`(venueService.createVenue(any(VenueCreateRequest::class.java))).thenReturn(venueResponse)

        mockMvc.perform(post("/api/v1/venues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(venueCreateRequest)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(venueResponse.id))
            .andExpect(jsonPath("$.name").value(venueResponse.name))
            .andExpect(jsonPath("$.location").value(venueResponse.location))
            .andExpect(jsonPath("$.totalSeats").value(venueResponse.totalSeats))

        verify(venueService, times(1)).createVenue(any(VenueCreateRequest::class.java))
    }

    @Test
    fun `should get venue by id`() {
        `when`(venueService.getVenueById(1)).thenReturn(venueResponse)

        mockMvc.perform(get("/api/v1/venues/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(venueResponse.id))
            .andExpect(jsonPath("$.name").value(venueResponse.name))
            .andExpect(jsonPath("$.location").value(venueResponse.location))
            .andExpect(jsonPath("$.totalSeats").value(venueResponse.totalSeats))

        verify(venueService, times(1)).getVenueById(1)
    }

    @Test
    fun `should get all venues`() {
        val venuesDto = VenuesDto(
             "Sample Venue",
             "New York",
             1000
        )
        val venuesList = listOf(venuesDto)

        `when`(venueService.getAllVenues()).thenReturn(venuesList)

        mockMvc.perform(get("/api/v1/venues"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value(venuesDto.name))
            .andExpect(jsonPath("$[0].location").value(venuesDto.location))
            .andExpect(jsonPath("$[0].totalSeats").value(venuesDto.totalSeats))

        verify(venueService, times(1)).getAllVenues()
    }

    @Test
    fun `should update venue`() {
        val updatedVenueResponse = VenueResponse(
             1,
             "Updated Venue",
             "Updated Description",
            "Update Location",
             25000
        )

        `when`(venueService.updateVenue(eq(1), any(VenueUpdateRequest::class.java))).thenReturn(updatedVenueResponse)

        mockMvc.perform(put("/api/v1/venues/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(venueUpdateRequest)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(updatedVenueResponse.id))
            .andExpect(jsonPath("$.name").value(updatedVenueResponse.name))
            .andExpect(jsonPath("$.location").value(updatedVenueResponse.location))
            .andExpect(jsonPath("$.totalSeats").value(updatedVenueResponse.totalSeats))

        verify(venueService, times(1)).updateVenue(eq(1), any(VenueUpdateRequest::class.java))
    }

    @Test
    fun `should delete venue`() {
        doNothing().`when`(venueService).deleteVenue(1)

        mockMvc.perform(delete("/api/v1/venues/1"))
            .andExpect(status().isOk)
            .andExpect(content().string("Deleted successfully"))

        verify(venueService, times(1)).deleteVenue(1)
    }
}
