package com.example.dataflowsrevice.controller;

import com.example.dataflowsrevice.model.Event;
import com.example.dataflowsrevice.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
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

    @GetMapping
    public Slice<Event> getEventsByType(@RequestParam(defaultValue = "") String type,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam ("sort")String sortField) {
        Slice<Event> resultSet = eventService.findByType(type, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,sortField)));

        log.info("getEventsByType {}",
                resultSet.stream().toArray());
        return eventService.findByType(type, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortField)));
    }


}
