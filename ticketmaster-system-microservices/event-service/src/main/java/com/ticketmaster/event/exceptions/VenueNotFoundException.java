package com.ticketmaster.event.exceptions;

public class VenueNotFoundException extends RuntimeException{

    public VenueNotFoundException(){
        super();
    }

    public VenueNotFoundException(String msg){
        super(msg);
    }
}
