package com.example.dataflowsrevice.controller;

import com.example.dataflowsrevice.model.Event;
import com.example.dataflowsrevice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/all")
    public List<Event> showAllEvents() {
        return eventService.findAll();
    }

}
