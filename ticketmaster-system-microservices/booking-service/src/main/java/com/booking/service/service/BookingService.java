package com.booking.service.service;

import com.booking.service.dto.request.ReserveRequest;
import com.booking.service.dto.response.ReserveResponse;
import com.booking.service.entity.Booking;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface BookingService {


     CompletableFuture<ReserveResponse> processReserveTickets(ReserveRequest reserveRequest) throws InterruptedException;

    CompletableFuture<ReserveResponse> reserveTickets(ReserveRequest reserveRequest) throws InterruptedException;

    void confirmBooking(Long reservationId) throws ExecutionException, InterruptedException;

    Booking saveBooking(Booking booking);
}
