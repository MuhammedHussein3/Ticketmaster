package com.ticket.service.mapper;

import com.ticket.service.dto.SeatStatus;
import com.ticket.service.dto.TicketCreateRequest;
import com.ticket.service.dto.TicketResponse;
import com.ticket.service.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mapper {

    public List<Ticket> convertToTickets(Long eventId, List<TicketCreateRequest> tickets){
      return tickets.stream()
              .map((t)->Ticket.builder()
                       .eventId(eventId)
                       .seatId(t.seatId())
                       .status(SeatStatus.AVAILABLE)
                       .section(t.section())
                       .row(t.row())
                       .number(t.number())
                       .price(t.price())
                       .build()
                )
                .toList();
    }

    public List<TicketResponse> convertToTicketResponse(List<Ticket> tickets){
        return tickets.stream()
                .map((t)->TicketResponse.builder()
                                .ticketId(t.getId())
                                .eventId(t.getEventId())
                                .seatId(t.getSeatId())
                                .price(t.getPrice())
                                .status(t.getSeatId())
                                .build()
                        )
                .toList();
    }

}
