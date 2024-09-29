package com.notification.service.mapper;

import com.notification.service.dto.QRCodeDetails;
import com.notification.service.dto.TicketDetailsMessage;
import com.notification.service.dto.User;
import org.springframework.stereotype.Service;

@Service
public class Mapper {


    public QRCodeDetails convertToQRCodeDetails(Long ticketId, String seatId, TicketDetailsMessage ticketDetailsMessage, User user){
        return QRCodeDetails.builder()
                .ticketId(ticketId)
                .seatId(seatId)
                .reservedId(ticketDetailsMessage.reservedId())
                .firstName(user.getFirsName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .build();
    }
}
