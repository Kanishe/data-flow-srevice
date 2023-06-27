package com.example.dataflowsrevice.controller;

import com.example.dataflowsrevice.exceptions.EventNotBeCreated;
import com.example.dataflowsrevice.model.Event;
import com.example.dataflowsrevice.service.EventService;
import com.example.dataflowsrevice.service.KafkaService;
import com.example.dataflowsrevice.utilities.FakerGen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController // @Controller + @ResponseBody над каждым методом
@Slf4j
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final FakerGen fakeGeneration;
    private final KafkaService kafkaService;
    private final ObjectMapper objectMapper;

    @Autowired
    public EventController(EventService eventService, FakerGen fakeGeneration, KafkaService kafkaService, ObjectMapper mapper) {
        this.eventService = eventService;
        this.fakeGeneration = fakeGeneration;
        this.kafkaService = kafkaService;
        this.objectMapper = mapper;
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
        Slice<Event> resultSet = eventService.findByType
                (type, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortField)));
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
    @Transactional
    public void addNewEvent() {
        for (int i = 0; i < 10; i++) {
            Event event = new Event();
            event.setType(fakeGeneration.faker().company().industry());
            event.setBusinessValue(fakeGeneration.faker().hacker().noun());
            event.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            event.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            log.info("New data was inserted {}", eventService.saveEvent(event));
            eventService.saveEvent(event);
        }
    }

    //генерилка тестовых данных Rest (запись в БД)
    @PostConstruct
    public void sentRq() throws JsonProcessingException {
        String urlForRs = "https://reqres.in/api/users/2";
        String urlForRq = "https://reqres.in/api/users";

        HashMap<String, String> hashMapForRq = new HashMap<>();
        hashMapForRq.put("animal", fakeGeneration.faker().animal().name());
        HttpEntity<Map<String, String>> request = new HttpEntity<>(hashMapForRq);

        RestTemplate restTemplate = new RestTemplate();
        String rQ = restTemplate.postForObject(urlForRq, request, String.class);
        String rS = restTemplate.getForObject(urlForRs, String.class);

        JsonNode jsonNode = objectMapper.readTree(rQ);
        log.info("Fake response: {} ", rS);
        log.info("Fake response after faker rq: {} ", jsonNode.get("animal"));
    }

    @GetMapping("/getEvents")
    @ResponseBody
    public List<Event> getEventRest() {
        return eventService.findAll();
    }


    @PostMapping("/sentEvent")
    public Event createEvent(@RequestBody @Valid Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error:errors){
                errMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";")
                        .append("\n");
            }
            throw new EventNotBeCreated(errMessage.toString());
        }
        Event savedEvent = eventService.saveEvent(event);
        kafkaService.sendEvent(savedEvent);
        log.info("New Event was taken and save to DB : {}", savedEvent);
        return savedEvent;

    }
}
