package com.booking.service.exceptions;

public class BookingNotFoundException extends RuntimeException{

    public BookingNotFoundException(){
        super();
    }

    public BookingNotFoundException(String msg){
        super(msg);
    }
}
