package com.ticketmaster.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Mono;

import java.util.Objects;

@SpringBootApplication
public class TicketmasterGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketmasterGatewayServiceApplication.class, args);
	}



	@Bean
	KeyResolver ipKeyResolver() {
		return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
	}

}
