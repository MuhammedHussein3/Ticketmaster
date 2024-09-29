package com.booking.service.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaBookingConfig {

    @Bean
    public NewTopic bookingTopic(){
        return TopicBuilder
                .name("reservation-notification-topic")
                .build();
    }

    @Bean
    public NewTopic ticketsTopic(){
        return TopicBuilder
                .name("tickets-notification-topic")
                .build();
    }
}
