package com.ticketmaster.event.exceptions;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(){
        super();
    }
    public EventNotFoundException(String message){
        super(message);
    }
}
