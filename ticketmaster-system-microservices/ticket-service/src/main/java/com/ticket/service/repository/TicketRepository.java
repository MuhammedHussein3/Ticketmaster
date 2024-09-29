package com.ticket.service.repository;

import com.ticket.service.dto.SeatStatus;
import com.ticket.service.entity.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {


    @Modifying
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            UPDATE Ticket t
            SET t.status = :seatBooked
            WHERE t.id IN :ticketIds
            """)
    void updateSeatStatusToBooked(@Param("seatBooked") SeatStatus seatBooked,
                                  @Param("ticketIds") List<Long> ticketIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT t
            FROM Ticket t
            WHERE t.id IN :ticketsId AND t.status = :seatAvailable
            """)
    List<Ticket> findAvailableSeats(@Param("ticketsId") List<Long> ticketsId,
                                    @Param("seatAvailable") SeatStatus seatAvailable);

    @Query("""
            SELECT t
            FROM Ticket t
            WHERE t.eventId = :eventId
            """)
    Optional<List<Ticket>> findTicketsByEvent(@Param("eventId") Long eventId);

    @Query("""
            SELECT t
            FROM Ticket t
            WHERE t.userId = :userId
            """)
    Optional<List<Ticket>> findTicketsByUser(@Param("userId") Long userId);

    @Query("""
            SELECT t
            FROM Ticket t
            WHERE t.id IN :ticketsId AND t.status = :seatBooked
            """)
    List<Ticket> findReservationSeats(@Param("ticketsId") List<Long> ticketsId,
                                      @Param("seatBooked") SeatStatus seatBooked);
}
