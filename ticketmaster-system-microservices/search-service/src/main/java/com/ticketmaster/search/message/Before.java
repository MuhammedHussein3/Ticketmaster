package com.ticketmaster.search.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Before {

    @JsonProperty("available_seats")
    private Integer available_seats;

    @JsonProperty("category_id")
    private Integer category_id;

    @JsonProperty("venue_id")
    private Integer venue_id;

    @JsonProperty("created_at")
    private String created_at;

    @JsonProperty("end_time")
    private String end_time;

    @JsonProperty("event_id")
    private Long event_id;

    @JsonProperty("start_time")
    private String start_time;

    @JsonProperty("updated_at")
    private String updated_at;

    @JsonProperty("description")
    private String description;

    @JsonProperty("name")
    private String name;
}

