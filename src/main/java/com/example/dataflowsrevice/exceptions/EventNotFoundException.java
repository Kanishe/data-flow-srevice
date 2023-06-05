package com.example.dataflowsrevice.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long id) {
        super("Event not found with ID: " + id);
    }
}
