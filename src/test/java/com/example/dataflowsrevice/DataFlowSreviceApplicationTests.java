package com.example.dataflowsrevice;

import com.example.dataflowsrevice.controller.EventController;
import com.example.dataflowsrevice.model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataFlowSreviceApplicationTests {

    @Autowired
    EventController eventController;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldAccept(){
        Event events = eventController.showAllEvents().get(1);
        String e = events.getEventId().toString();
        System.out.println(e);
        Assertions.assertEquals("2",e);
    }


    @Test
    void currdate(){
        String data = "28.10.2023";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(data,dateTimeFormatter);
       Assertions.assertEquals(localDate.getDayOfMonth(),28);
    }

}
