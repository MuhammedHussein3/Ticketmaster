package com.ticketmaster.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryRequest(

        @NotBlank(message = "Name is mandatory")
        @Size(max = 255, message = "Name should not exceed 255 characters")
        String name,
        @NotBlank(message = "Description is mandatory")
        @Size(max = 500, message = "Description should not exceed 500 characters")
        String description

) {
}
