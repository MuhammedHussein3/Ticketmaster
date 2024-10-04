package com.ticketmaster.event.exceptions;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(){
        super();
    }

    public CategoryNotFoundException(String msg){
        super(msg);
    }
}
