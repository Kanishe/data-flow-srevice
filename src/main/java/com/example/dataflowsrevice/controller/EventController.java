package com.example.dataflowsrevice.controller;

import com.example.dataflowsrevice.model.Event;
import com.example.dataflowsrevice.service.EventService;
import com.example.dataflowsrevice.utilities.FakerGen;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final FakerGen fakeGeneration;

    @Autowired
    public EventController(EventService eventService, FakerGen fakeGeneration) {
        this.eventService = eventService;
        this.fakeGeneration = fakeGeneration;
    }

    @GetMapping("/all")
    public List<Event> showAllEvents() {
        return eventService.findAll();
    }

    @GetMapping
    public Slice<Event> getEventsByType(@RequestParam(defaultValue = "") String type,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam("sort") String sortField) {
        Slice<Event> resultSet = eventService.findByType(type, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortField)));

        log.info("getEventsByType {}",
                resultSet.stream().toArray());
        return eventService.findByType(type, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortField)));
    }

    @GetMapping("/getById/{id}")
    public Event getEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        log.info("was find event by id: {}",
                event);
        return event;
    }

    //генерилка тестовых данных (запись в БД)
    @PostConstruct
    public void addNewEvent() {

        for (int i = 0; i < 1000; i++) {
            Event event = new Event();
            event.setType(fakeGeneration.faker().name().fullName());
            event.setBusinessValue(fakeGeneration.faker().name().fullName());
            event.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            event.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            eventService.saveEvent(event);
        }
        ;

    }
}
