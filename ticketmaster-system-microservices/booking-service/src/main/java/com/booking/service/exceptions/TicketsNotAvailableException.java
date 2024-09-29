package com.booking.service.exceptions;

public class TicketsNotAvailableException extends RuntimeException{

    public TicketsNotAvailableException(){
        super();
    }

    public TicketsNotAvailableException(String msg){
        super(msg);
    }
}
