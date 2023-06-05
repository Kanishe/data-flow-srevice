package com.example.dataflowsrevice.service;

import com.example.dataflowsrevice.exceptions.EventNotFoundException;
import com.example.dataflowsrevice.model.Event;
import com.example.dataflowsrevice.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
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


    public Slice<Event> findByType(String type, Pageable pageable) {
        return eventRepository.findByType(type, pageable);
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException());
    }

    @Transactional
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

}
