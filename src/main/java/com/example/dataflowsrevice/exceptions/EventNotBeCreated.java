package com.example.dataflowsrevice.exceptions;

import com.example.dataflowsrevice.model.Event;

public class EventNotBeCreated extends RuntimeException{
    public EventNotBeCreated (String msg){
        super(msg);
    }
}
