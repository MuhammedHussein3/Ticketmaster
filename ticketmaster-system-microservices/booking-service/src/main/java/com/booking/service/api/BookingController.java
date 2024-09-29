package com.booking.service.api;

import com.booking.service.dto.enums.BookingStatus;
import com.booking.service.dto.request.ReserveRequest;
import com.booking.service.dto.response.ReserveResponse;
import com.booking.service.service.BookingService;
import com.booking.service.ticket.dto.UpdateSeatsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {


    private final BookingService bookingService;

    @PostMapping("/reserve")
    public ResponseEntity<ReserveResponse> reserveTickets(
            @RequestBody ReserveRequest reserveRequest
            ) throws ExecutionException, InterruptedException {

        return ResponseEntity.ok(bookingService.processReserveTickets(reserveRequest).get());
    }


    @PutMapping("/confirm/{reservation-id}")
    public ResponseEntity<?> confirmBooking(@PathVariable("reservation-id") Long reservationId) throws ExecutionException, InterruptedException {

        bookingService.confirmBooking(reservationId);
        return ResponseEntity.ok("updated success");
    }

}
