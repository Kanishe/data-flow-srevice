package com.example.dataflowsrevice.service;

import com.example.dataflowsrevice.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaService {
    private final KafkaTemplate<String, Event> kafkaTemplate;
    @Value("${spring.kafka.producer.topic}")
    private String topic;


    @Autowired
    public KafkaService(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Event event) {
        kafkaTemplate.send(topic, event);
    }
}
