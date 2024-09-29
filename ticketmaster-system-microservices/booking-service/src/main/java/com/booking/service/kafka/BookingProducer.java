package com.booking.service.kafka;

import com.booking.service.message.BookingConfirmationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookingProducer {



        private final KafkaTemplate<String, BookingConfirmationMessage> kafkaTemplate;


        public void sendBookingConfirmation(BookingConfirmationMessage bookingConfirmationMessage){
            log.info("Sending Ticket reserved Confirmation From Booking Service to Notification Service in Ticketmaster System");


            Message<BookingConfirmationMessage> message =
                    MessageBuilder
                            .withPayload(bookingConfirmationMessage)
                            .setHeader(KafkaHeaders.TOPIC, "reservation-notification-topic")
                            .build();

            kafkaTemplate.send(message);

    }
}
