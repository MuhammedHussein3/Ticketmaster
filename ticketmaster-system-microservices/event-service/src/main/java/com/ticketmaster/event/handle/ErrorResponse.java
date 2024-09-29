package com.ticketmaster.event.handle;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}

