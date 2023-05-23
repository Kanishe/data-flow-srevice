package com.example.dataflowsrevice.service;

import com.example.dataflowsrevice.model.Event;
import com.example.dataflowsrevice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)// по умолчанию для всех методов, кроме тех у которых будет переопределена @Transactional
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Transactional
    public void save() {
    }
}
