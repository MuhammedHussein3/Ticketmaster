package com.notification.service.kafka;

import com.google.zxing.WriterException;
import com.notification.service.dto.BookingConfirmationMessage;
import com.notification.service.dto.TicketDetailsMessage;
import com.notification.service.service.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import java.io.IOException;
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "reservation-notification-topic")
    public void consumeCustomerEventCreated(BookingConfirmationMessage bookingConfirmationMessage){
        notificationService.sendBookingConfirmation(bookingConfirmationMessage);
    }

    @KafkaListener(topics = "tickets-notification-topic")
    public void consumeTicketDetails(TicketDetailsMessage ticketDetailsMessage) throws IOException, WriterException {
        notificationService.sendTicketDetailsWithQRCodes(ticketDetailsMessage);
    }
}
