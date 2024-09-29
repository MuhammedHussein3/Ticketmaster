package com.ticketmaster.event.dto;

import com.ticketmaster.event.entity.Category;
import com.ticketmaster.event.entity.Venue;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * EventResponse is a DTO (Data Transfer Object) that represents the response
 * for an event in the system. This class includes essential event details
 * such as the event name, description, available seats, start and end times,
 * venue, and category. It is used to transfer event data between layers,
 * specifically when responding to client requests.
 *
 * - The class uses Lombok annotations for easier code generation:
 *   - @Builder: Enables the Builder pattern for easier object creation.
 *   - @AllArgsConstructor: Generates a constructor with all fields.
 *   - @NoArgsConstructor: Generates a default no-argument constructor.
 *   - @Getter and @Setter: Automatically generates getter and setter methods for all fields.
 *
 * The class also implements Serializable, allowing instances to be serialized for distributed systems
 * or for caching purposes.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventResponse implements Serializable {
   private String name;
   private String description;
   private Integer availableSeats;
   private LocalDateTime startTime;
   private LocalDateTime endTime;
   private Venue venue;
   private Category category;
}
