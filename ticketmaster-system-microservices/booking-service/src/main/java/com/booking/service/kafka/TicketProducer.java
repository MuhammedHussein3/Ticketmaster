package com.booking.service.kafka;

import com.booking.service.message.BookingConfirmationMessage;
import com.booking.service.message.TicketDetailsMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketProducer {

    private final KafkaTemplate<String, TicketDetailsMessage> kafkaTemplate;


    public void sendTicketDetails(TicketDetailsMessage ticketDetailsMessage){
        log.info("Sending Ticket reserved Confirmation From Booking Service to Notification Service in Ticketmaster System");


        Message<TicketDetailsMessage> message =
                MessageBuilder
                        .withPayload(ticketDetailsMessage)
                        .setHeader(KafkaHeaders.TOPIC, "tickets-notification-topic")
                        .build();

        kafkaTemplate.send(message);

    }
}
