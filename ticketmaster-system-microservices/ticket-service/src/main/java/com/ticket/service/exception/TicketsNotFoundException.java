package com.ticket.service.exception;

public class TicketsNotFoundException extends RuntimeException {

    public TicketsNotFoundException(){
        super();
    }
    public TicketsNotFoundException(String message) {
        super(message);
    }
}
