package com.ticketmaster.search.exceptions;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(){
        super();
    }

    public EventNotFoundException(String msg){
        super(msg);
    }
}
