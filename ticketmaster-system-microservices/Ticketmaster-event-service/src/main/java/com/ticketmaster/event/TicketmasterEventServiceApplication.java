package com.ticketmaster.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TicketmasterEventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketmasterEventServiceApplication.class, args);
	}

}
