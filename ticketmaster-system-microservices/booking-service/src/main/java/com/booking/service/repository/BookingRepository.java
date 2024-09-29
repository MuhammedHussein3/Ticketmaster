package com.booking.service.repository;

import com.booking.service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
